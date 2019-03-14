package com.lx.testandroid.testviews;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.lx.testandroid.R;
import com.lx.testandroid.recyclerview.FragmentTestActivity;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SocialViewPageTitleLayout extends RelativeLayout {

    private static final String TAG = SocialViewPageTitleLayout.class.getSimpleName();

    private ViewPager mViewPager;
    private LinearLayout mTitleContainer;

    static class TEST {

    }

    public SocialViewPageTitleLayout(Context context) {
        super(context);
        init();
    }

    public SocialViewPageTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SocialViewPageTitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        setBackgroundColor(0xffffffff);
        mTitleContainer = new LinearLayout(getContext());
        mTitleContainer.setGravity(Gravity.CENTER);
        mTitleContainer.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams containerLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                .WRAP_CONTENT);
        containerLp.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(mTitleContainer, containerLp);

        ImageView searchIcon = new ImageView(getContext());
        searchIcon.setImageResource(R.drawable.social_search_icon);
        searchIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        LayoutParams searchLp = new LayoutParams(dp2px(15), dp2px(15));
        searchLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        searchLp.addRule(RelativeLayout.CENTER_VERTICAL);
        searchLp.rightMargin = dp2px(12);
        addView(searchIcon, searchLp);

        TextView tv = new TextView(getContext());
        tv.setText("99");
        tv.setTextSize(11);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.WHITE);
        tv.setBackgroundColor(Color.RED);
        LayoutParams tvLp = new LayoutParams(dp2px(14), dp2px(15));
        tvLp.leftMargin = 10;
        tvLp.topMargin = 10;
        addView(tv, tvLp);
    }

    public void setViewPager(@NonNull ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled, position=" + position + ", positionOffset=" + positionOffset + ", "
                        + "positionOffsetPixels=" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected, position=" + position);
                onPageItemSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged, state=" + state);
            }
        });
    }

    boolean isAnimation;

    private static final int TOP = 1;
    private static final int BUTTON = 2;

    @IntDef({TOP, BUTTON})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationDirection {
    }

    private void startAnimation(@AnimationDirection int direction) {
        Animation animation;
        switch (direction) {
            case TOP:
                animation = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, -1.0f);
                break;
            case BUTTON:
                animation = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, -1.0f,
                        Animation.RELATIVE_TO_SELF, 0);
                break;
            default:
                return;
        }
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimation = true;
                Log.d(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimation = false;
                Log.d(TAG, "onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        startAnimation(animation);
    }

    private void onPageItemSelected(final int position) {
        if (mTitleContainer == null || position + 1 > mTitleContainer.getChildCount()) {
            return;
        }
        int count = mTitleContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            RelativeLayout rl = (RelativeLayout) mTitleContainer.getChildAt(i);
            SocialTitleTextView tv = (SocialTitleTextView) rl.getChildAt(0);
            tv.setTextColor(i == position ? 0xfffe4070 : 0xff666666);
            tv.drawLine(i == position);
        }

//        if (!isShown()) {
//            setVisibility(View.VISIBLE);
//            startAnimation(BUTTON);
//        }
        FragmentTestActivity.SocialIndexBaseFragment fragment =
                (FragmentTestActivity.SocialIndexBaseFragment) ((FragmentStatePagerAdapter) mViewPager.getAdapter()).getItem(position);
//        RecyclerView recyclerView = (RecyclerView) fragment.getView().findViewById(R.id.recycler_view);
//        if (recyclerView != null) {
//            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//                @Override
//                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//                    Log.d(TAG, "recyclerView onScrolled, dx=" + dx + ", dy=" +dy);
//                    if (isAnimation) {
//                        return;
//                    }
//                    if (dy > 5 && isShown()) {
////                        startAnimation(TOP);
////                        new Handler().postDelayed(new Runnable() {
////                            @Override
////                            public void run() {
////                                setVisibility(View.GONE);
////                            }
////                        }, 500);
//                    } else if (dy < -5 && !isShown()) {
////                        startAnimation(BUTTON);
////                        setVisibility(View.VISIBLE);
//                    }
//                }
//
//                @Override
//                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                    super.onScrollStateChanged(recyclerView, newState);
//                    Log.d(TAG, "recyclerView onScrollStateChanged, newState=" + newState);
//                }
//            });
//        }
    }

    public void setTitles(String... titles) {
        if (mTitleContainer == null) {
            return;
        }
        int length = titles.length;
        for (int i = 0; i < length; i++) {
            final int index = i;
            View view = View.inflate(getContext(), R.layout.social_pager_title_item, null);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mViewPager != null) {
                        mViewPager.setCurrentItem(index);
                    }
                }
            });
            String title = titles[i];
            SocialTitleTextView tv = (SocialTitleTextView) view.findViewById(R.id.title_name);
            tv.setText(title);
            if (i == 0) {
                tv.drawBigDotWithNum(true, 9);
            } else if (i == 1) {
                tv.drawDot(true);
            } else if (i == 2) {
                tv.drawNewIcon(true);
            }
            float width = tv.getPaint().measureText((String) tv.getText());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int) width, dp2px(40));
            //            lp.rightMargin = dp2px(5);
            //            lp.leftMargin = dp2px(5);
            mTitleContainer.addView(view, lp);
        }
        onPageItemSelected(0);
        requestLayout();
        invalidate();
    }

    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
