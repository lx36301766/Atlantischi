package com.lx.testandroid.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lx.testandroid.R;
import com.lx.testandroid.view.CheckView;

public class CheckCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_code);

        CheckView checkView = (CheckView) findViewById(R.id.checkView);

    }
}
