package com.lx.testandroid.util;

import java.util.Random;

import com.lx.testandroid.App;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.TypedValue;

/**
 * Created on 04/09/2017.
 *
 * @author lx
 */

public class NativeSupplier {

    private Paint mTextPaint = new Paint();
    private Paint mLinePaint = new Paint();

    private static final int POINT_NUM = 100;
    private static final int LINE_NUM = 10;
    private static final float TEXT_SIZE_SP = 20;

    public NativeSupplier(Context context) {
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(sp2px(TEXT_SIZE_SP));
        mTextPaint.setStrokeWidth(3);

        mLinePaint.setAntiAlias(true);
        mLinePaint.setTextSize(sp2px(TEXT_SIZE_SP));
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setTextAlign(Paint.Align.CENTER);
    }

    public Bitmap getVerifyCodeBitmap(String code, int width, int height) {
        if (TextUtils.isEmpty(code)) {
            return null;
        }
        int textLength = code.length();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(randomBackgroundColor());
        mTextPaint.setColor(randomTextColor());
        mLinePaint.setColor(randomTextColor());
        int dx = width / textLength / 2;
        for (int i = 0; i < textLength; i++) {
            // 绘制验证控件上的文本
            String text = "" + code.charAt(i);
            Rect rect = new Rect();
            mTextPaint.getTextBounds(text, 0, text.length(), rect);
            int textHeight = rect.height();
            int saveCount = canvas.save();
            canvas.rotate(new Random().nextInt(30) - 15, width / 2f, height / 2f);
            canvas.drawText(text, dx, getTextY(height, textHeight), mTextPaint);
            canvas.restoreToCount(saveCount);
            dx += width / (textLength + 1);
        }
        int[] line;
        for (int i = 0; i < LINE_NUM; i++) {
            // 划线
            line = getLine(height, width);
            canvas.drawLine(line[0], line[1], line[2], line[3], mLinePaint);
        }
        // 绘制小圆点
        int[] point;
        for (int i = 0; i < POINT_NUM; i++) {
            // 画点
            point = getPoint(height, width);
            canvas.drawCircle(point[0], point[1], 1, mLinePaint);
        }
        return bitmap;
    }

    /**
     * @return 随机背景颜色
     */
    private static int randomBackgroundColor() {
        Random random = new Random();
        int red = 255 - random.nextInt(125);
        int green = 255 - random.nextInt(125);
        int blue = 255 - random.nextInt(125);
        return Color.rgb(red, green, blue);
    }

    /**
     * @return 随机文字颜色
     */
    private static int randomTextColor() {
        Random random = new Random();
        int red = random.nextInt(75) + 10;
        int green = random.nextInt(75) + 10;
        int blue = random.nextInt(75) + 10;
        return Color.rgb(red, green, blue);
    }

    /**
     * 计算验证码的绘制y点位置
     *
     * @param height 传入CheckView的高度值
     *
     * @return
     */
    public static int getTextY(int height, int textHeight) {
        int tmpY = (int) ((height - textHeight) * Math.random());
        int boundary = (int) dp2px(8);
        tmpY = Math.min(tmpY, height - textHeight - boundary);
        tmpY = Math.max(tmpY, boundary);
        return tmpY + textHeight;
    }

    /**
     * 随机产生划线的起始点坐标和结束点坐标
     *
     * @param height 传入CheckView的高度值
     * @param width  传入CheckView的宽度值
     *
     * @return 起始点坐标和结束点坐标
     */
    public static int[] getLine(int height, int width) {
        int[] tempCheckNum = {0, 0, 0, 0};
        for (int i = 0; i < 4; i += 2) {
            tempCheckNum[i] = (int) (Math.random() * width);
            tempCheckNum[i + 1] = (int) (Math.random() * height);
        }
        return tempCheckNum;
    }

    /**
     * 随机产生点的圆心点坐标
     *
     * @param height 传入CheckView的高度值
     * @param width  传入CheckView的宽度值
     *
     * @return
     */
    public static int[] getPoint(int height, int width) {
        int[] tempCheckNum = {0, 0, 0, 0};
        tempCheckNum[0] = (int) (Math.random() * width);
        tempCheckNum[1] = (int) (Math.random() * height);
        return tempCheckNum;
    }

    public static float dp2px(float spVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, spVal, App.sContext.getResources()
                .getDisplayMetrics());
    }

    public static float sp2px(float spVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, App.sContext.getResources()
                .getDisplayMetrics());
    }

}
