package com.lx.testandroid.hlistview.hlist;

import android.view.ActionMode;

/**
 * Created by xuanluo on 16/10/24.
 */

public interface MultiChoiceModeListener extends ActionMode.Callback {
    public void onItemCheckedStateChanged( ActionMode mode, int position, long id, boolean checked );
}