package com.lx.testandroid.recyclerview.scrollhelper;

import com.lx.testandroid.R;
import com.lx.testandroid.recyclerview.observablescrollview.CacheFragmentStatePagerAdapter;
import com.lx.testandroid.recyclerview.observablescrollview.ObservableScrollViewCallbacks;
import com.lx.testandroid.recyclerview.observablescrollview.ScrollState;
import com.lx.testandroid.recyclerview.observablescrollview.ScrollUtils;
import com.lx.testandroid.recyclerview.observablescrollview.Scrollable;
import com.lx.testandroid.recyclerview.observablescrollview.TouchInterceptionFrameLayout;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

public class SocialScrollerHelper
        implements TouchInterceptionFrameLayout.TouchInterceptionListener, ObservableScrollViewCallbacks {

    private static final String TAG = SocialScrollerHelper.class.getSimpleName();

    private int mSlop;
    private TouchInterceptionFrameLayout mInterceptionLayout;
    private boolean mScrolled;
    private ScrollState mLastScrollState;

    View mPagerTitleView;
    Activity mActivity;
    ViewPager mViewPager;

    public SocialScrollerHelper(Activity activity) {
        mActivity = activity;

        ViewConfiguration vc = ViewConfiguration.get(activity);
        mSlop = vc.getScaledTouchSlop();
        mInterceptionLayout = (TouchInterceptionFrameLayout) activity.findViewById(R.id.container);
        mInterceptionLayout.setScrollInterceptionListener(this);

        mPagerTitleView = activity.findViewById(R.id.page_title);
        mViewPager = (ViewPager) activity.findViewById(R.id.view_pager);
    }

    @Override
    public boolean shouldInterceptTouchEvent(MotionEvent ev, boolean moving, float diffX, float diffY) {
        if (!mScrolled && mSlop < Math.abs(diffX) && Math.abs(diffY) < Math.abs(diffX)) {
            // Horizontal scroll is maybe handled by ViewPager
            return false;
        }

        Scrollable scrollable = getCurrentScrollable();
        if (scrollable == null) {
            mScrolled = false;
            return false;
        }

        // If interceptionLayout can move, it should intercept.
        // And once it begins to move, horizontal scroll shouldn't work any longer.
        int toolbarHeight = mPagerTitleView.getHeight();
        int translationY = (int) getTranslationY();
        boolean scrollingUp = 0 < diffY;
        boolean scrollingDown = diffY < 0;
        if (scrollingUp) {
            if (translationY < 0) {
                mScrolled = true;
                mLastScrollState = ScrollState.UP;
                return true;
            }
        } else if (scrollingDown) {
            if (-toolbarHeight < translationY) {
                mScrolled = true;
                mLastScrollState = ScrollState.DOWN;
                return true;
            }
        }
        mScrolled = false;
        return false;
    }

    @Override
    public void onDownMotionEvent(MotionEvent ev) {
    }

    @Override
    public void onMoveMotionEvent(MotionEvent ev, float diffX, float diffY) {
        float translationY = ScrollUtils.getFloat(getTranslationY() + diffY, -mPagerTitleView.getHeight(), 0);
        setTranslationY(translationY);
        if (translationY < 0) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mInterceptionLayout.getLayoutParams();
            lp.height = (int) (-translationY + mInterceptionLayout.getHeight());
            mInterceptionLayout.requestLayout();

//            int height = (int) (-translationY + mInterceptio nLayout.getHeight());
//            RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.MATCH_PARENT, height);
//            lp2.addRule(RelativeLayout.ABOVE, R.id.toolbar);
//            mInterceptionLayout.setLayoutParams(lp2);
//            mInterceptionLayout.requestLayout();
            //Xlog.d(TAG, "lp.height=%s, translationY=%s", lp.height, translationY);
        }
    }

    @Override
    public void onUpOrCancelMotionEvent(MotionEvent ev) {
        mScrolled = false;
        adjustToolbar(mLastScrollState);
    }


    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if (!mScrolled) {
            // This event can be used only when TouchInterceptionFrameLayout
            // doesn't handle the consecutive events.
            adjustToolbar(scrollState);
        }
    }

    private Scrollable getCurrentScrollable() {
        Fragment fragment = getCurrentFragment();
        if (fragment == null) {
            return null;
        }
        View view = fragment.getView();
        if (view == null) {
            return null;
        }
        return (Scrollable) view.findViewById(R.id.recycler_view);
    }

    private Fragment getCurrentFragment() {
        CacheFragmentStatePagerAdapter pagerAdapter = (CacheFragmentStatePagerAdapter) mViewPager.getAdapter();
        return pagerAdapter.getItemAt(mViewPager.getCurrentItem());
    }

    private void adjustToolbar(ScrollState scrollState) {
        int toolbarHeight = mPagerTitleView.getHeight();
        final Scrollable scrollable = getCurrentScrollable();
        if (scrollable == null) {
            return;
        }
        int scrollY = scrollable.getCurrentScrollY();
        if (scrollState == ScrollState.DOWN) {
            showToolbar();
        } else if (scrollState == ScrollState.UP) {
            if (toolbarHeight <= scrollY) {
                hideToolbar();
            } else {
                showToolbar();
            }
        } else if (!toolbarIsShown() && !toolbarIsHidden()) {
            // Toolbar is moving but doesn't know which to move:
            // you can change this to hideToolbar()
            showToolbar();
        }
    }

    private boolean toolbarIsShown() {
        return getTranslationY() == 0;
    }

    private boolean toolbarIsHidden() {
        return getTranslationY() == -mPagerTitleView.getHeight();
    }

    private void showToolbar() {
        animateToolbar(0);
    }

    private void hideToolbar() {
        animateToolbar(-mPagerTitleView.getHeight());
    }

    private void animateToolbar(final float toY) {
        float layoutTranslationY = getTranslationY();
        if (layoutTranslationY != toY) {
            ValueAnimator animator = ValueAnimator.ofFloat(getTranslationY(), toY).setDuration(200);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float translationY = (float) animation.getAnimatedValue();
                    setTranslationY(translationY);
                    if (translationY < 0) {
                        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mInterceptionLayout.getLayoutParams();
                        lp.height = (int) (-translationY + mInterceptionLayout.getHeight());
                        mInterceptionLayout.requestLayout();
                    }
                }
            });
            animator.start();
        }
    }

    private float getTranslationY() {
        return mInterceptionLayout.getTranslationY();
    }

    private void setTranslationY(float translationY) {
        mInterceptionLayout.setTranslationY(translationY);
    }

}
