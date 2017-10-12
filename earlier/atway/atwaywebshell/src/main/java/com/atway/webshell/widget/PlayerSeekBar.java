package com.atway.webshell.widget;

import android.content.Context;
import android.graphics.*;
import android.view.View;
import com.atway.webshell.R;
import com.atway.webshell.utils.Xlog;

public class PlayerSeekBar extends View{

    private static final String TAG = "PlayerSeekBar";

    private int forwardSec = 10;
    private Bitmap bg;
    private int bgY;
    /****************游标提示框*******************/
    private int tipBgColor = 0xfff86f5f;
    private int tipCornerRadius = 5;
    private int tipTxtColor = 0xffffffff;
    private int tipWidth;
    private int tipHeight;
    private int tipPaddingTB ;
    private int tipPaddingLR ;
    private int tipBarSpace ;

    private int tipCursorWidth ;
    private int tipCursorHeight ;
    private Point cursorLeft = new Point();
    private Point cursorRight= new Point();
    private Point cursorbottom= new Point();
    private Path cursorPath;
    private int tipX;
    private int tipY;

    /*****************期望高宽 只适配固定16:9 ***********************/
    private static final int DESIGN_HEIGHT = 1080;
    private static int SCREEN_HEIGHT = 1080;
    private static int SCREEN_WIDTH = 1920;

    /***********************进度条信息*******************/
    private int baseLineY ;
    private int barWidth ;
    private int barHeight;
    private int barX;
    private int barY;

    private int space ;

    private int firstBarColor = 0x66ffffff;
    private int secondBarColor = 0x5ff86f5f;
    private int thirdBarColor = 0xfff86f5f;

    /*********************文本信息***********************/
    private int txtColor = 0x7fffffff;
    private int txtSize;



    private String leftTxt = "00:00";
    private int leftTxtWidth;
    private int leftTxtX;
    private int leftTxtY;

    private String tipTxt = "00:00";
    private int tipTxtWidth;
    private int tipTxtOffset;

    private String rightTxt = "00:00";
    private int rightTxtWidth;
    private int rightTxtX;
    private int rightTxtY;

    /************************进度条数据**************************/
    private long allLength=0;
    private long tipPosition=0;
    private long currentPosition=0;
    private float scale;

    private int tipCenterX;
    private int currentPositionX;
    /**************************画笔*****************************/
    private Paint firstLayerPaint;
    private Paint secondLayerPaint;
    private Paint thirdLayerPaint;
    private Paint textPaint;
    private Paint tipPaint;

    private boolean isNeedToDrawSecondLayer = false;



    public PlayerSeekBar(Context context, int screenWidth, int screenHeight) {
        super(context);
        SCREEN_WIDTH = screenWidth;
        SCREEN_HEIGHT = screenHeight;
        loadBgBitmap(context);
        initData();
        scale = allLength*1.0f/barWidth;
        initPaints();
        calcAllStaticStuffCoordinate();
    }

    private void loadBgBitmap(Context context){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Rect r = new Rect();
        BitmapFactory.decodeStream(context.getResources().openRawResource(R.drawable.seekbar_bg), r, options) ;
        Xlog.d(TAG, "before--r:" + r + ",op:" + options);
        options.outHeight = adapterFrom1080p(options.outHeight);
        options.outWidth = SCREEN_WIDTH;
        options.inJustDecodeBounds = false;
        bg = BitmapFactory.decodeStream(context.getResources().openRawResource(R.drawable.seekbar_bg), r, options);
        Xlog.d(TAG, "after--r:" + r + ",op:" + options);
        bgY = SCREEN_HEIGHT-bg.getHeight();
    }

    public void setSeekBarColor(int thirdBarColor,int secondBarColor ,int tipBgColor,int tipTxtColor){
        Xlog.d(TAG, "thirdColor:\"" + thirdBarColor + "\",secondColor:" + secondBarColor+"tipBgColor:\"" + tipBgColor + "\",tipTxtColor:" + tipTxtColor);
        this.thirdBarColor = thirdBarColor;
        this.secondBarColor = secondBarColor;
        this.tipBgColor = tipBgColor;
        this.tipTxtColor = tipTxtColor;
        if(secondLayerPaint==null){
            secondLayerPaint = new Paint();
            secondLayerPaint.setAntiAlias(true);
        }
        secondLayerPaint.setColor(secondBarColor);
        if(thirdLayerPaint==null) {
            thirdLayerPaint = new Paint();
            thirdLayerPaint.setAntiAlias(true);
        }
        thirdLayerPaint.setColor(thirdBarColor);
    }

    private void initPaints(){
        tipPaint = new Paint();
        tipPaint.setStyle(Paint.Style.FILL);
        tipPaint.setAntiAlias(true);
        tipPaint.setTextSize(txtSize);

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setColor(txtColor);
        textPaint.setTextSize(txtSize);

        firstLayerPaint = new Paint();
        firstLayerPaint.setAntiAlias(true);
        firstLayerPaint.setColor(firstBarColor);

        secondLayerPaint = new Paint();
        secondLayerPaint.setAntiAlias(true);
        secondLayerPaint.setColor(secondBarColor);

        thirdLayerPaint = new Paint();
        thirdLayerPaint.setAntiAlias(true);
        thirdLayerPaint.setColor(thirdBarColor);
    }

    public void setAllSize(long allSize){
        allLength = allSize;
        scale = allLength*1.0f/barWidth;
        calcAllLength();
    }

    private void initData(){
         tipPaddingTB = adapterFrom1080p(8);
         tipPaddingLR = adapterFrom1080p(20);
         tipBarSpace = adapterFrom1080p(18);
         tipCursorWidth = adapterFrom1080p(30);
         tipCursorHeight = adapterFrom1080p(20);


         baseLineY = adapterFrom1080p(1010);
         barWidth = adapterFrom1080p(1375);
         barHeight = adapterFrom1080p(20);
         space = adapterFrom1080p(35);
         txtSize = adapterFrom1080p(34);
    }

    private void calcCurrentPosition(){
        String leftTmp = changeLongToTimeString(currentPosition);
        if(leftTxt.length()!=leftTmp.length()||leftTxtWidth==0){
            leftTxtWidth = (int)(textPaint.measureText(leftTmp)+0.5f);
        }
        leftTxtX = barX-space-leftTxtWidth;
        leftTxt = leftTmp;
        currentPositionX = barX + (int)(currentPosition/scale);
    }



    private void calcAllLength(){
        String rightTmp = changeLongToTimeString(allLength);
        if(rightTxt.length()!=rightTmp.length()||rightTxtWidth==0){
            rightTxtWidth = (int)(textPaint.measureText(rightTmp)+0.5f);
        }

        rightTxt = rightTmp;
    }

    private void calcAllStaticStuffCoordinate(){
        barX = SCREEN_WIDTH/2-barWidth/2;
        barY = baseLineY - barHeight/2;
        Paint.FontMetricsInt font = textPaint.getFontMetricsInt();
        rightTxtY = leftTxtY = baseLineY +(font.descent-font.ascent)/2-font.descent;

        rightTxtX = SCREEN_WIDTH/2+barWidth/2+space;
        tipHeight = 2*tipPaddingTB + (font.bottom-font.top);
        tipY = barY - tipBarSpace - tipCursorHeight - tipHeight;
        tipTxtOffset = tipPaddingTB-font.top;
        cursorRight.y = cursorLeft.y = barY - tipBarSpace - tipCursorHeight;
        cursorbottom.y = barY - tipBarSpace;

        calcAllLength();
        calcCurrentPosition();
        calcTipPosition();
//        Log.d("demo", "barX:" + barX + ",barY:" + barY + ",txtY:" + rightTxtY + ",leftTxtX:" + leftTxtX + ",rightTxtX:" + rightTxtX + ",tipY:" + tipY);
    }

    /**
     * 计算 提示的位置
     */
    private void calcTipPosition(){
        String tipTmp = changeLongToTimeString(tipPosition);
        if(tipTxt.length()!=tipTmp.length()||tipTxtWidth==0){
            tipTxtWidth = (int)(textPaint.measureText(tipTmp)+0.5f);
            tipWidth = tipTxtWidth+2*tipPaddingLR;
        }
        tipTxt = tipTmp;
        tipCenterX = barX + (int)(tipPosition/scale);
        tipX = tipCenterX-tipWidth/2;
//        Log.d("demo", "tipCen:" + tipCenterX + ",tipX:" + tipX);
        cursorLeft.x = tipCenterX - tipCursorWidth/2;
        cursorRight.x = cursorLeft.x + tipCursorWidth;
        cursorbottom.x = tipCenterX;
        if(cursorPath==null)
            cursorPath = new Path();
        cursorPath.reset();
        cursorPath.moveTo(cursorLeft.x, cursorLeft.y);
        cursorPath.lineTo(cursorbottom.x, cursorbottom.y);
        cursorPath.lineTo(cursorRight.x, cursorRight.y);
        cursorPath.close();
    }



    private String changeLongToTimeString(long length){
        int hour = (int)length/(60*60);
        int min = (int)length%(60*60)/60;
        int sec = (int)length%60;
        StringBuilder sb = new StringBuilder();
        if(hour!=0) {
            if (hour < 10)
                sb.append("0" + hour);
             else
                sb.append(hour);
            sb.append(":");
        }
        if(min<10){
            sb.append("0"+min);
        }else
            sb.append(min);
        sb.append(":");
        if(sec<10){
            sb.append("0"+sec);
        }else
            sb.append(sec);
//        Log.d("demo", "length:" + length + ",Format:" + sb.toString());
        return sb.toString();
    }







    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawARGB(0,0,0,0);
        if(bg!=null){
            canvas.drawBitmap(bg,0,bgY,null);
        }
        drawLeftTxt(canvas);
        drawProgress(canvas);
        drawRightTxt(canvas);
        if(allLength!=0) drawTip(canvas);
    }



    public void plusTipPosition(){
        tipPosition+=forwardSec++;
        if(tipPosition>(allLength-10)){
            tipPosition = (allLength-10);
        }
        calcTipPosition();
    }
    public void reduceTipPosition(){
        tipPosition-=forwardSec++;
        if(tipPosition<0){
            tipPosition = 0;
        }
        calcTipPosition();
    }

    public void stopMoveTip(){
        forwardSec = 10;
    }

    public long getCurrentTipPosition(){

        return tipPosition;
    }



    public void updateCurrentPosition(long position,boolean isUpdateTip){
        if(position>allLength){
            Xlog.e(TAG,"allLength:"+allLength+", currentPosition:"+position+",wrong data!");
            return ;
        }
        currentPosition = position;
        calcCurrentPosition();
        if(isUpdateTip){
            tipPosition = currentPosition;
            calcTipPosition();
        }
    }


    /**
     * 画 左边文字
     * @param canvas
     */
    private void drawLeftTxt(Canvas canvas){
        canvas.drawText(leftTxt, leftTxtX, leftTxtY, textPaint);
    }

    /**
     * 画 进度条
     * @param canvas
     */
    private void drawProgress(Canvas canvas){
        RectF firstRect = new RectF(barX,barY,barX+barWidth,barY+barHeight);
        canvas.drawRoundRect(firstRect,barHeight/2,barHeight/2,firstLayerPaint);
        RectF secondRect = null;
        RectF thirdRect ;
        if(currentPosition>tipPosition){
            isNeedToDrawSecondLayer = true;
            secondRect = new RectF(barX,barY,currentPositionX,barY+barHeight);
            thirdRect = new RectF(barX,barY,tipCenterX,barY+barHeight);
        }else if(currentPosition<tipPosition){
            isNeedToDrawSecondLayer = true;
            secondRect = new RectF(barX,barY,tipCenterX,barY+barHeight);
            thirdRect = new RectF(barX,barY,currentPositionX,barY+barHeight);
        }else{
            isNeedToDrawSecondLayer = false;
            thirdRect = new RectF(barX,barY,currentPositionX,barY+barHeight);
        }

        if(isNeedToDrawSecondLayer) {
            canvas.drawRoundRect(secondRect, barHeight / 2, barHeight / 2, secondLayerPaint);
        }
        //如果进度还没出圆角 则画 (=()=============) 右移一个圆角到切割的进度条画布上。
        if(thirdRect.width()<barHeight){
            Path clipPath = new Path();
            clipPath.addRoundRect(firstRect,barHeight/2,barHeight/2,Path.Direction.CW);
            canvas.save();
            canvas.clipPath(clipPath);
            RectF tmp = new RectF(barX-barHeight + thirdRect.width(),barY,barX+thirdRect.width(),barY+barHeight);
            canvas.drawRoundRect(tmp, barHeight / 2, barHeight/2, thirdLayerPaint);
            canvas.restore();
        }else{
            canvas.drawRoundRect(thirdRect,barHeight/2,barHeight/2,thirdLayerPaint);
        }
    }

    /**
     * 画 右边文字
     * @param canvas
     */
    private void drawRightTxt(Canvas canvas){
//        Log.d("demo", "rightTxtX:" + rightTxtX + ",txt:" + rightTxt);
        canvas.drawText(rightTxt,rightTxtX,rightTxtY,textPaint);
    }

    /**
     * 画 提示 框
     * @param canvas
     */
    private void drawTip(Canvas canvas){
        RectF rect = new RectF(tipX,tipY,tipX+tipWidth,tipY+tipHeight);
        tipPaint.setColor(tipBgColor);
        canvas.drawRoundRect(rect, tipCornerRadius, tipCornerRadius, tipPaint);
        tipPaint.setColor(tipTxtColor);
        canvas.drawText(tipTxt, tipX + tipPaddingLR, tipY + tipTxtOffset, tipPaint);
        tipPaint.setColor(tipBgColor);

        canvas.drawPath(cursorPath,tipPaint);
    }

    /**
     * 屏幕分辨率 适配
     * @param Original
     * @return
     */
    public static int adapterFrom1080p(float Original) {
        return (int) (SCREEN_HEIGHT* 1.0f/ DESIGN_HEIGHT * Original  + 0.5f);
    }
}
