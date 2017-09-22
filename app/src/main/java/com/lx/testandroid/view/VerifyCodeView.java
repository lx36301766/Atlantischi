package com.lx.testandroid.view;

import java.util.Random;

import com.lx.testandroid.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kongqw on 2015/10/23.
 */
public class VerifyCodeView extends View {

    Paint mTextPaint = new Paint();
    Paint mLinePaint = new Paint();
    private final int mPointNum;
    private final int mLineNum;
    private int mTextLength;
    private final float mTextSize;
    private String mText;


    // 验证码
    public VerifyCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VerifyCodeView);
        // 获取随机点的个数
        mPointNum = a.getInteger(R.styleable.VerifyCodeView_point_num, 0);
        // 获取随机线的条数
        mLineNum = a.getInteger(R.styleable.VerifyCodeView_line_num, 0);
        // 获取验证码长度
        mTextLength = a.getInteger(R.styleable.VerifyCodeView_text_length, 4);
        // 获取验证码字体大小
        mTextSize = a.getDimension(R.styleable.VerifyCodeView_text_size, 30);
        a.recycle();

        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setStrokeWidth(3);

        mLinePaint.setAntiAlias(true);
        mLinePaint.setTextSize(mTextSize);
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(randomBackgroundColor());
        mTextPaint.setColor(randomTextColor());
        mLinePaint.setColor(randomTextColor());
        final int height = getHeight();
        // 获得CheckView控件的高度
        final int width = getWidth();
        // 获得CheckView控件的宽度
        int dx = width / mTextLength / 2;
        for (int i = 0; i < mTextLength; i++) {
            // 绘制验证控件上的文本
            String text = "" + mText.charAt(i);
            Rect rect = new Rect();
            mTextPaint.getTextBounds(text, 0, text.length(), rect);
            int textHeight = rect.height();
            int saveCount = canvas.save();
            canvas.rotate(new Random().nextInt(30) - 15, width / 2f, height / 2f);
            canvas.drawText(text, dx, getTextY(height, textHeight), mTextPaint);
            canvas.restoreToCount(saveCount);
            dx += width / (mTextLength + 1);
        }
        int[] line;
        for (int i = 0; i < mLineNum; i++) {
            // 划线
            line = getLine(height, width);
            canvas.drawLine(line[0], line[1], line[2], line[3], mLinePaint);
        }
        // 绘制小圆点
        int[] point;
        for (int i = 0; i < mPointNum; i++) {
            // 画点
            point = getPoint(height, width);
            canvas.drawCircle(point[0], point[1], 1, mLinePaint);
        }
    }

    public void setText(String text) {
        mText = text;
        mText = "8888";
        mTextLength = mText.length();
    }

    /**
     * @return 随机背景颜色
     */
    private int randomBackgroundColor() {
        Random random = new Random();
        int red = 255 - random.nextInt(125);
        int green = 255 - random.nextInt(125);
        int blue = 255 - random.nextInt(125);
        return Color.rgb(red, green, blue);
    }

    /**
     * @return 随机文字颜色
     */
    private int randomTextColor() {
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
    public int getTextY(int height, int textHeight) {
        int tmpY = (int) ((height - textHeight) * Math.random());
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

}