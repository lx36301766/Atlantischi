package com.lx.testandroid.recyclerview.layoutmanager;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * CustomLayoutManager
 * HowToCustomLayoutManager <com.vilyever.howtocustomlayoutmanager>
 * Created by vilyever on 2016/1/7.
 * Feature:
 * 展示一个斜线布局的示例
 * 支持横向纵向滑动
 */
public class CustomLayoutManager extends RecyclerView.LayoutManager {
    /** Convenience Var to call this */

    public CustomLayoutManager() {
    }

    /** @see android.support.v7.widget.RecyclerView.LayoutManager#onLayoutChildren(RecyclerView.Recycler, RecyclerView.State) */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) { // 跳过preLayout，preLayout主要用于支持动画，暂时先使用自带的简单的fading
            return;
        }
        detachAndScrapAttachedViews(recycler); // 分离所有的itemView

        mState.contentWidth = 0;
        mState.contentHeight = 0;

        if (getItemCount() == 0) {
            mState.scrolledY = 0;
            mState.totalSpreadCount = 0;
            return;
        }

        mState.totalSpreadCount = 1;

        int offsetX = 0; // 当前X偏移，下一个item的left为此值
        int offsetY = 0; // 当前Y偏移，下一个item的top为此值
        int occupiedLineBlock = 0; // 当前line已被占用的块数

        /**
         * {@link #orientation} 为 {@link #HORIZONTAL} 时，当前line的最大宽度
         * {@link #orientation} 为 {@link #VERTICAL} 时，当前line的最大高度
         */
        int currentLineSpreadLength = 0;

        /**
         * {@link #orientation} 为 {@link #HORIZONTAL} 时，当前line的总高度
         * {@link #orientation} 为 {@link #VERTICAL} 时，当前line的总宽度
         */
        int currentLineLength = 0;

        /**
         * {@link #orientation} 为 {@link #HORIZONTAL} 时，所有line的最大高度
         * {@link #orientation} 为 {@link #VERTICAL} 时，所有line的最大宽度
         */
        int maxLineLength = 0;

        /**
         * 计算所有item的frame，以及总宽高
         */
        for (int i = 0; i < getItemCount(); i++) {
            View scrap = recycler.getViewForPosition(i); // 根据position获取一个碎片view，可以从回收的view中获取，也可能新构造一个

            addView(scrap);
            measureChildWithMargins(scrap, 0, 0);  // 计算此碎片view包含边距的尺寸

            int width = getDecoratedMeasuredWidth(scrap);  // 获取此碎片view包含边距和装饰的宽度width
            int height = getDecoratedMeasuredHeight(scrap); // 获取此碎片view包含边距和装饰的高度height

            LayoutParams layoutParams = (LayoutParams) scrap.getLayoutParams();
            occupiedLineBlock += Math.max(layoutParams.occupationLineBlocks, 1); // 记录当前line已占用的块数，每个item至少占1块
            /** 占用的块数若超过了{@link com.vilyever.howtocustomlayoutmanager.CustomLayoutManager.State#itemLineCount}，切换到下一line */
            if (occupiedLineBlock > mState.itemLineCount) {

                offsetX = 0; // 横向重置到0
                offsetY += currentLineSpreadLength; // 纵向偏移当前line的最大高度
                mState.contentHeight += currentLineSpreadLength; // contentHeight增加当前line的最大高度

                occupiedLineBlock = layoutParams.occupationLineBlocks; // 切换到新line布置item
                currentLineSpreadLength = 0; // 新line还没有item，不占空间
                maxLineLength = Math.max(maxLineLength, currentLineLength); // 记录之前所有line的最大长度
                currentLineLength = 0; // 重置
                mState.totalSpreadCount++;
            }

            Rect frame = mState.itemsFrames.get(i); // 若先前生成过Rect，重复使用
            if (frame == null) {
                frame = new Rect();
            }

            frame.set(offsetX, offsetY, offsetX + width, offsetY + height);
            mState.itemsFrames.put(i, frame); // 记录每个item的frame
            mState.itemsAttached.put(i, false); // 因为先前已经回收了所有item，此处将item显示标识置否

            offsetX += width; // 横向偏移，纵向不变
            currentLineSpreadLength = Math.max(currentLineSpreadLength, height); // 记录当前line最大高度
            currentLineLength += width; // 记录当前line总宽度

            detachAndScrapView(scrap, recycler); // 回收本次计算的碎片view
        }

        mState.contentWidth = maxLineLength; // contentWidth设为所有line的最大宽度
        mState.contentHeight += currentLineSpreadLength; // contentWidth增加最后line的最大高度

        mState.contentWidth = Math.max(mState.contentWidth, getHorizontalSpace()); // 内容宽度最小为RecyclerView容器宽度
        mState.contentHeight = Math.max(mState.contentHeight, getVerticalSpace()); // 内容高度最小为RecyclerView容器高度

        // 依照新内容宽高调整记录的滑动距离，防止滑动偏移过大
        fixScrollOffset();

        layoutItems(recycler, state); // 放置当前scroll offset处要展示的item
    }

    /** @see RecyclerView.LayoutManager#canScrollHorizontally() */
    @Override
    public boolean canScrollHorizontally() {
        return false;
    }

    /** @see RecyclerView.LayoutManager#canScrollVertically() */
    @Override
    public boolean canScrollVertically() {
        return true;
    }

    /** @see android.support.v7.widget.RecyclerView.LayoutManager#scrollVerticallyBy(int, RecyclerView.Recycler, RecyclerView.State) */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int willScroll = dy;

        /**
         * 限制滑动距离的最小值和最大值
         */
        if (mState.scrolledY + dy < 0) {
            willScroll = -mState.scrolledY;
        }
        else if (mState.scrolledY + dy > mState.contentHeight - getVerticalSpace()) {
            willScroll = mState.contentHeight - getVerticalSpace() - mState.scrolledY;
        }

        // 如果将要滑动的距离为0，返回-dy以显示边缘光晕
        if (willScroll == 0) {
            return -dy;
        }

        mState.scrolledY += willScroll;

        // 平移容器内的item
        offsetChildrenVertical(-willScroll);
        // 移除屏幕外的item，添加当前可显示的新item
        layoutItems(recycler, state);

        return willScroll;
    }

    @Override
    public void scrollToPosition(int position) {
        position = Math.max(position, 0);
        position = Math.min(position, getItemCount());

        mState.scrolledY = mState.itemsFrames.get(position).top;
        fixScrollOffset();

        requestLayout();
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        position = Math.max(position, 0);
        position = Math.min(position, getItemCount());

        /*
         * LinearSmoothScroller's default behavior is to scroll the contents until
         * the child is fully visible. It will snap to the top-left or bottom-right
         * of the parent depending on whether the direction of travel was positive
         * or negative.
         */
        LinearSmoothScroller scroller = new LinearSmoothScroller(recyclerView.getContext()) {
            /*
             * LinearSmoothScroller, at a minimum, just need to know the vector
             * (x/y distance) to travel in order to get from the current positioning
             * to the target.
             */
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                int oldScrollY = mState.scrolledY;

                mState.scrolledY = mState.itemsFrames.get(targetPosition).top;
                fixScrollOffset();

                int newScrollY = mState.scrolledY;

                mState.scrolledY = oldScrollY;

                return new PointF(0, newScrollY - oldScrollY);
            }
        };
        scroller.setTargetPosition(position);
        startSmoothScroll(scroller);
    }

    /** @see RecyclerView.LayoutManager#generateDefaultLayoutParams() */
    @Override
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /** @see RecyclerView.LayoutManager#generateLayoutParams(ViewGroup.LayoutParams) */
    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) lp);
        } else {
            return new LayoutParams(lp);
        }
    }

    /** @see RecyclerView.LayoutManager#generateLayoutParams(Context, AttributeSet) */
    @Override
    public RecyclerView.LayoutParams generateLayoutParams(Context c, AttributeSet attrs) {
        return new LayoutParams(c, attrs);
    }

    /** @see RecyclerView.LayoutManager#checkLayoutParams(RecyclerView.LayoutParams) */
    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
        return lp instanceof LayoutParams;
    }

    public static class LayoutParams extends RecyclerView.LayoutParams {

        /**
         * 占用块数
         * 不同方向布局时，占用行数或列数的值
         * {@link State#itemLineCount}
         * default is 1, must > 0
         */
        public int occupationLineBlocks = 1;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }
        public LayoutParams(int width, int height) {
            super(width, height);
        }
        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }
        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
        public LayoutParams(RecyclerView.LayoutParams source) {
            super(source);
        }
    }

    /**
     * 摆放当前状态下要展示的item
     * @param recycler         Recycler to use for fetching potentially cached views for a
     *                         position
     * @param state            Transient state of RecyclerView
     */
    private void layoutItems(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) { // 跳过preLayout，preLayout主要用于支持动画
            return;
        }

        // 当前scroll offset状态下的显示区域
        Rect displayFrame = new Rect(0, mState.scrolledY, getHorizontalSpace(), mState.scrolledY + getVerticalSpace());

        /**
         * 移除已显示的但在当前scroll offset状态下处于屏幕外的item
         */
        Rect childFrame = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            childFrame.left = getDecoratedLeft(child);
            childFrame.top = getDecoratedTop(child);
            childFrame.right = getDecoratedRight(child);
            childFrame.bottom = getDecoratedBottom(child);

            if (!Rect.intersects(displayFrame, childFrame)) {
                mState.itemsAttached.put(getPosition(child), false);
                removeAndRecycleView(child, recycler);
            }
        }

        /**
         * 摆放需要显示的item
         * 由于RecyclerView实际上并没有scroll，也就是说RecyclerView容器的滑动效果是依赖于LayoutManager对item进行平移来实现的
         * 故在放置item时要将item的计算位置平移到实际位置
         */
        for (int i = 0; i < getItemCount(); i++) {
            if (Rect.intersects(displayFrame, mState.itemsFrames.get(i))) {
                /**
                 * 在onLayoutChildren时由于移除了所有的item view，可以遍历全部item进行添加
                 * 在scroll时就不同了，由于scroll时会先将已显示的item view进行平移，然后移除屏幕外的item view，此时仍然在屏幕内显示的item view就无需再次添加了
                 */
                if (!mState.itemsAttached.get(i)) {
                    View scrap = recycler.getViewForPosition(i);
                    measureChildWithMargins(scrap, 0, 0);
                    addView(scrap);

                    Rect frame = mState.itemsFrames.get(i);
                    layoutDecorated(scrap,
                            frame.left,
                            frame.top - mState.scrolledY,
                            frame.right,
                            frame.bottom - mState.scrolledY); // Important！布局到RecyclerView容器中，所有的计算都是为了得出任意position的item的边界来布局

                    mState.itemsAttached.put(i, true);
                }
            }
        }
    }


    /**
     * 记录当前LayoutManager的一些信息
     */
    private State mState = new State();
    
//    public State mState {
//        if (mState == null) {
//            mState = new State();
//        }
//        return mState;
//    }

    class State {
        /**
         * 存放所有item的位置和尺寸
         */
        SparseArray<Rect> itemsFrames;

        /**
         * 记录item是否已经展示
         */
        SparseBooleanArray itemsAttached;

        /**
         * 纵向滑动距离
         * @see #scrollVerticallyBy(int, RecyclerView.Recycler, RecyclerView.State)
         */
        int scrolledY;

        /**
         * 内容宽度
         * note：最小宽度为容器宽度
         */
        int contentWidth;

        /**
         * 内容高度
         * note：最小高度为容器高度
         */
        int contentHeight;

        /**
         * {@link #orientation} 为 {@link #HORIZONTAL} 时，每列显示的item数
         * {@link #orientation} 为 {@link #VERTICAL} 时，每行显示的item数
         * 每个item有可能占用多格 {@link LayoutParams#occupationLineBlocks}
         */
        int itemLineCount;

        /**
         * {@link #orientation} 为 {@link #HORIZONTAL} 时，表示列数
         * {@link #orientation} 为 {@link #VERTICAL} 时，表示行数
         */
        int totalSpreadCount;

        public State() {
            itemsFrames = new SparseArray<>();
            itemsAttached = new SparseBooleanArray();
            scrolledY = 0;
            contentWidth = 0;
            contentHeight = 0;
            itemLineCount = 1;
            totalSpreadCount = 0;
        }
    }

    /**
     * 纵然此LayoutManager在水平方向和垂直方向都可以滑动
     * 此LayoutManager仍然带有orientation属性
     * orientation将影响item摆放的次序
     *
     * 若Direction为Vertical，item的摆放顺序为从左到右，一行铺满后填充下一行
     * 若Direction为Horizontal，item的摆放顺序为从上到下，一列铺满后填充下一列
     */
    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {}

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    public CustomLayoutManager setItemLineCount(int itemLineCount) {
        assertNotInLayoutOrScroll(null);
        if (itemLineCount == mState.itemLineCount) {
            return this;
        }
        mState.itemLineCount = itemLineCount;
        requestLayout();
        return this;
    }

    public int getItemLineCount() {
        return mState.itemLineCount;
    }

    /**
     * 依照内容宽高调整记录的滑动距离，防止滑动偏移过大
     */
    private void fixScrollOffset() {
        if (mState.contentHeight == getVerticalSpace()) {
            mState.scrolledY = 0;
        }
        if (mState.scrolledY > (mState.contentHeight - getVerticalSpace())) {
            mState.scrolledY = mState.contentHeight - getVerticalSpace();
        }
    }

    /**
     * 容器去除padding后的宽度
     * @return 实际可摆放item的空间
     */
    private int getHorizontalSpace() {
        return getWidth() - getPaddingRight() - getPaddingLeft();
    }

    /**
     * 容器去除padding后的高度
     * @return 实际可摆放item的空间
     */
    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

}