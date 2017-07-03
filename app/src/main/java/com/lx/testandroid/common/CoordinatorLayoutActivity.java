package com.lx.testandroid.common;

import com.lx.testandroid.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created on 28/06/2017.
 *
 * @author lx
 */

public class CoordinatorLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinator_test);
        CoordinatorLayout root = (CoordinatorLayout) findViewById(R.id.root);
//        Snackbar.make(root, "FAB", Snackbar.LENGTH_LONG)
//                .setAction("cancel", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //这里的单击事件代表点击消除Action后的响应事件
//                        }
//                })
//                .show();

    }
}
