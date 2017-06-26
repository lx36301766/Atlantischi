package com.lx.testandroid.study.realm;

import com.lx.testandroid.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created on 12/05/2017.
 *
 * @author lx
 */

public class RealmActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_realm);
    }
}
