package com.lx.testandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

/**
 * Created by xuanluo on 2016/12/30.
 */

public class NumberTextView extends TextView {

    public NumberTextView(Context context) {
        super(context);
    }

    public NumberTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    class NumberTextAnimation extends Animation {

        private int mStart;
        private int mEnd;

        private final int step = 20;

        public NumberTextAnimation(int start, int end) {
            mStart = start;
            mEnd = 5644454;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            if (interpolatedTime == 1.0f) {
                mStart = mEnd;
            } else {
                int diff = mEnd - mStart;
                mStart += diff / step;
            }
            String time = String.valueOf(interpolatedTime);
            if (time.length() > 4) {
                time = time.substring(0, 4);
            }
            setText(mStart + " " + time);
        }
    }

    public void setNum(final int start, final int end) {
        Animation animation = new NumberTextAnimation(start, end);
        animation.setDuration(2000);
        startAnimation(animation);
    }

}
