package com.lx.testandroid.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by xuanluo on 16/11/25.
 */

public class SocialViewPager extends ViewPager {

    public SocialViewPager(Context context) {
        super(context);
    }

    public SocialViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mCanSwitchPage) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private boolean mCanSwitchPage;

    public void switchScroll() {
        mCanSwitchPage = !mCanSwitchPage;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mCanSwitchPage) {
            return false;
        }
        return super.onTouchEvent(ev);
    }
}
