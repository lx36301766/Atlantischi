package com.lx.testandroid.testviews;

import com.lx.testandroid.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.widget.TextView;

public class SocialTitleTextView extends TextView {

    private int mFlag;

    public static final int DRAW_DOT_FLAG = 0x0001;
    public static final int DRAW_LINE_FLAG = 0x0002;
    public static final int DRAW_BIG_NUM_FLAG = 0x0004;
    public static final int DRAW_NEW_ICON_FLAG = 0x0010;

    private Rect mNumRect = new Rect();
    private RectF mDotRect = new RectF();
    private Rect mLineRect;
    private Rect mTextRect;

    private Paint mTextPaint;
    private Paint mDotPaint;
    private Paint mStrokePaint;

    private NinePatchDrawable mLineDrawable;

    private Bitmap mNewIconBitmap;

    private long mNum;

    public SocialTitleTextView(Context context) {
        this(context, null);
    }

    public SocialTitleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SocialTitleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDotPaint = new Paint();
        mDotPaint.setColor(0xfffe4070);
        mDotPaint.setAntiAlias(true);

        mLineDrawable = (NinePatchDrawable) getResources().getDrawable(R.drawable.social_top_line);
        mTextRect = new Rect();

        mTextPaint = new Paint();
        //mTextPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(BaseDensityUtil.sp2px(getContext(), 11));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        mStrokePaint = new Paint();
        mStrokePaint.setColor(0xffffffff);
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setStrokeWidth(BaseDensityUtil.dip2px(getContext(), 1.0f));
        mStrokePaint.setStyle(Paint.Style.STROKE);

        mNewIconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_tab_new);
    }

    private void addFlag(int flag) {
        mFlag |= flag;
    }

    private void removeFlag(int flag) {
        mFlag &= ~flag;
    }

    public boolean isDrawDot() {
        return (mFlag & DRAW_DOT_FLAG) == DRAW_DOT_FLAG;
    }

    public boolean isDrawLine() {
        return (mFlag & DRAW_LINE_FLAG) == DRAW_LINE_FLAG && mLineDrawable != null;
    }

    public boolean isDrawBigNum() {
        return (mFlag & DRAW_BIG_NUM_FLAG) == DRAW_BIG_NUM_FLAG;
    }

    public boolean isDrawNewIcon() {
        return (mFlag & DRAW_NEW_ICON_FLAG) == DRAW_NEW_ICON_FLAG && mNewIconBitmap != null;
    }

    public void setText(String str) {
        str = "     " + str + "     ";
        super.setText(str);
    }

    public void drawBigDotWithNum(boolean draw, long num) {
        if (draw) {
            addFlag(DRAW_BIG_NUM_FLAG);
        } else {
            removeFlag(DRAW_BIG_NUM_FLAG);
        }
        mNum = num;
        postInvalidate();
    }

    public void drawDot(boolean draw) {
        if (draw) {
            addFlag(DRAW_DOT_FLAG);
        } else {
            removeFlag(DRAW_DOT_FLAG);
        }
        postInvalidate();
    }

    public void drawLine(boolean draw) {
        if (draw) {
            addFlag(DRAW_LINE_FLAG);
        } else {
            removeFlag(DRAW_LINE_FLAG);
        }
        postInvalidate();
    }

    public void drawNewIcon(boolean draw) {
        if (draw) {
            addFlag(DRAW_NEW_ICON_FLAG);
        } else {
            removeFlag(DRAW_NEW_ICON_FLAG);
        }
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDot(canvas);
        drawLine(canvas);
        drawNumDot(canvas);
        drawNewIcon(canvas);
    }

    private void drawNewIcon(Canvas canvas) {
        if (isDrawNewIcon()) {
            float textWidth = getPaint().measureText(getText().toString());
            float left = textWidth - BaseDensityUtil.dip2px(getContext(), 30);
            float top = BaseDensityUtil.dip2px(getContext(), 4);
            canvas.drawBitmap(mNewIconBitmap, left,top, null);
        }
    }

    private void drawDot(Canvas canvas) {
        if (isDrawDot()) {
            String str = getText().toString();
            getPaint().getTextBounds(str, 0, str.length(), mTextRect);
            float textHeight = mTextRect.height();
            float textWidth = getPaint().measureText(str);
            float radius = BaseDensityUtil.dip2px(getContext(), 3);
            canvas.drawCircle(textWidth - radius * 5, (getHeight() - textHeight) / 2 -
                    BaseDensityUtil.dip2px(getContext(), 2), radius, mDotPaint);
        }
    }

    private void drawLine(Canvas canvas) {
        if (isDrawLine()) {
            String str = getText().toString();
            float textWidth = getPaint().measureText(str);
            if (mLineRect == null) {
                float trimTextWidth = getPaint().measureText(str.trim());
                int delta = (int) ((textWidth - trimTextWidth) / 2);
                mLineRect = new Rect(delta, getHeight() - mLineDrawable.getIntrinsicHeight(), (int) trimTextWidth +
                        delta, getHeight());
            }
            mLineDrawable.setBounds(mLineRect);
            mLineDrawable.draw(canvas);
        }
    }

    private void drawNumDot(Canvas canvas) {
        if (isDrawBigNum()) {
            String str = getText().toString();
            getPaint().getTextBounds(str, 0, str.length(), mTextRect);
            float textHeight = mTextRect.height();
            float textWidth = getPaint().measureText(str);
            float radius = BaseDensityUtil.dip2px(getContext(), 7.0f);

            String numStr = mNum > 99 ? "99+" : String.valueOf(mNum);
            float numLen = (numStr.length() - 1) * BaseDensityUtil.dip2px(getContext(), 5.0f);

            float cx = textWidth - radius - BaseDensityUtil.dip2px(getContext(), 3.0f);
            float cy = (getHeight() - textHeight) / 2;

            float left = cx - radius * 2;
            float right = left + radius * 2 + numLen;
            float top = cy - radius;
            float bottom = top + radius * 2;
            mDotRect.set(left, top, right, bottom);

            float radian = BaseDensityUtil.dip2px(getContext(), 8);
            canvas.drawRoundRect(mDotRect, radian, radian, mDotPaint);
            canvas.drawRoundRect(mDotRect, radian, radian, mStrokePaint);

            mTextPaint.getTextBounds(numStr, 0, numStr.length(), mNumRect);
            canvas.drawText(numStr, left + radius / 2, cy + mNumRect.height() / 2, mTextPaint);
        }
    }



    public static class BaseDensityUtil {

        public static int dip2px(Context context, float dipValue) {
            float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dipValue * scale + 0.5f);
        }

        public static int sp2px(Context context, float spValue) {
            float scale = context.getResources()
                    .getDisplayMetrics().density;
            return (int) (spValue * scale + 0.5f);
        }

    }

}
