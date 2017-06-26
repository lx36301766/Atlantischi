package com.lx.testandroid.recyclerview.layoutmanager;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class SocialRecyclerView extends RecyclerView {

    private static final String TAG = SocialRecyclerView.class.getSimpleName();

    public SocialRecyclerView(Context context) {
        super(context);
    }

    public SocialRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SocialRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void init() {
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return getLayoutManager().canScrollVertically();
    }

}
