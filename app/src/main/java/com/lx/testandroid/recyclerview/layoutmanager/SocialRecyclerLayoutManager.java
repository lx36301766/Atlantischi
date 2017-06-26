package com.lx.testandroid.recyclerview.layoutmanager;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class SocialRecyclerLayoutManager extends RecyclerView.LayoutManager {

    private static final String TAG = SocialRecyclerLayoutManager.class.getSimpleName();

    private Context mContext;

    private ArrayList<Rect> mItemPosList = new ArrayList<>();
    private int mParentMaxHeight;
    private int mScrollOffsetY;
    private int mFixedTopOffset;


    public SocialRecyclerLayoutManager(Context context) {
        mContext = context;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.d(TAG, "onLayoutChildren, state=" + state);
        if (state.isPreLayout()) {
            return;
        }
        if (getItemCount() == 0) {
            return;
        }

        detachAndScrapAttachedViews(recycler);
        mParentMaxHeight = 0;
        mFixedTopOffset = dp2px(40);

        int offsetY = mFixedTopOffset;
        for (int i = 0; i < getItemCount(); i++) {
            View scrap = recycler.getViewForPosition(i);
            addView(scrap);
            measureChildWithMargins(scrap, 0, 0);

            int width = getDecoratedMeasuredWidth(scrap);
            int height = getDecoratedMeasuredHeight(scrap);
            Rect rect = new Rect();
            rect.set(0, offsetY, width, offsetY + height);
            mItemPosList.add(rect);

            offsetY += height;
            mParentMaxHeight += height;
            detachAndScrapView(scrap, recycler);
        }
        mParentMaxHeight = Math.max(mParentMaxHeight, getVerticalSpace());
        layoutChildren(recycler, state);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int scrollY = dy;
        if (mScrollOffsetY + dy < 0) {
            scrollY = -mScrollOffsetY;
        }
        else if (mScrollOffsetY + dy > mParentMaxHeight - getVerticalSpace() + mFixedTopOffset) {
            scrollY = mParentMaxHeight - getVerticalSpace() - mScrollOffsetY + mFixedTopOffset;
        }
        mScrollOffsetY += scrollY;
        offsetChildrenVertical(-scrollY);
        layoutChildren(recycler, state);
        return scrollY;
    }

    private void layoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
            return;
        }
        Rect displayFrame = new Rect(0, mScrollOffsetY, getHorizontalSpace(), mScrollOffsetY + getVerticalSpace());
        Rect childFrame = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            childFrame.left = getDecoratedLeft(child);
            childFrame.top = getDecoratedTop(child);
            childFrame.right = getDecoratedRight(child);
            childFrame.bottom = getDecoratedBottom(child);
            if (!Rect.intersects(displayFrame, childFrame)) {
                removeAndRecycleView(child, recycler);
            }
        }
        for (int i = 0; i < getItemCount(); i++) {
            if (Rect.intersects(displayFrame, mItemPosList.get(i))) {
                final View scrap = recycler.getViewForPosition(i);
                measureChildWithMargins(scrap, 0, 0);
                addView(scrap);
                Rect frame = mItemPosList.get(i);
                layoutDecorated(scrap, frame.left, frame.top - mScrollOffsetY, frame.right, frame.bottom - mScrollOffsetY);
            }
        }
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public int findLastCompletelyVisibleItemPosition() {
        final View child = findOneVisibleChild(getChildCount() - 1, -1, true, false);
        return child == null ? NO_POSITION : getPosition(child);
    }

    View findOneVisibleChild(int fromIndex, int toIndex, boolean completelyVisible, boolean acceptPartiallyVisible) {
        final int start = getPaddingTop();
        final int end = getHeight() - getPaddingBottom();
        final int next = toIndex > fromIndex ? 1 : -1;
        View partiallyVisible = null;
        for (int i = fromIndex; i != toIndex; i+=next) {
            final View child = getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int childStart = getDecoratedTop(child) + params.topMargin;
            final int childEnd = getDecoratedBottom(child) - params.bottomMargin;
            if (childStart < end && childEnd > start) {
                if (completelyVisible) {
                    if (childStart >= start && childEnd <= end) {
                        return child;
                    } else if (acceptPartiallyVisible && partiallyVisible == null) {
                        partiallyVisible = child;
                    }
                } else {
                    return child;
                }
            }
        }
        return partiallyVisible;
    }

    @Override
    public boolean canScrollHorizontally() {
        return false;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingRight() - getPaddingLeft();
    }

    public int dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
