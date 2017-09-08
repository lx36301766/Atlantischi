package com.lx.testandroid.common;

import java.util.Random;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lx.testandroid.R;
import com.lx.testandroid.view.VerifyCodeView;

public class CheckCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_code);

        final VerifyCodeView verifyCodeView = (VerifyCodeView) findViewById(R.id.checkView);
        verifyCodeView.setText("" + randomInterval(1000, 9999));

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCodeView.invalidate();
            }
        });
    }

    private int randomInterval(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

}
