package com.lx.testandroid.hlistview.hlist;

import android.content.Context;
import android.util.AttributeSet;
import it.sephiroth.android.library.widget.HListView;

/**
 * Created by xuanluo on 16/10/24.
 */

public class CustomHListView extends HListView {

    public CustomHListView(Context context) {
        super(context);
    }

    public CustomHListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomHListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Sets the selected item and positions the selection y pixels from the top edge
     * of the ListView. (If in touch mode, the item will not be selected but it will
     * still be positioned appropriately.)
     *
     * @param position Index (starting at 0) of the data item to be selected.
     * @param y The distance from the top edge of the ListView (plus padding) that the
     *        item will be positioned.
     */
    public void setSelectionFromTop(int position, int y) {
        if (mAdapter == null) {
            return;
        }

        if (!isInTouchMode()) {
            position = lookForSelectablePosition(position, true);
            if (position >= 0) {
                setNextSelectedPositionInt(position);
            }
        } else {
            mResurrectToPosition = position;
        }

        if (position >= 0) {
            mLayoutMode = LAYOUT_SPECIFIC;
            mSpecificLeft = mListPadding.top + y;

            if (mNeedSync) {
                mSyncPosition = position;
                mSyncColId = mAdapter.getItemId(position);
            }

            if (mPositionScroller != null) {
                mPositionScroller.stop();
            }
            requestLayout();
        }
    }
}
