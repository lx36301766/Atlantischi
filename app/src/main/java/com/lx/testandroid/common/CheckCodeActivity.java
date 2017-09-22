package com.lx.testandroid.common;

import java.util.Random;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lx.testandroid.R;
import com.lx.testandroid.util.NativeSupplier;
import com.lx.testandroid.view.VerifyCodeView;

public class CheckCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_code);

//        final VerifyCodeView verifyCodeView = (VerifyCodeView) findViewById(R.id.checkView);
//        verifyCodeView.setText("" + randomInterval(1000, 9999));

        final ImageView imageView = (ImageView) findViewById(R.id.verify_code_img);
        final NativeSupplier nativeSupplier = new NativeSupplier(this);
        imageView.setImageBitmap(nativeSupplier.getVerifyCodeBitmap("" + randomInterval(1000, 9999), 137, 50));

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                verifyCodeView.invalidate();
                int width = (int) NativeSupplier.sp2px(91.1f);
                int height = (int) NativeSupplier.sp2px(33.3f);
                imageView.setImageBitmap(nativeSupplier.getVerifyCodeBitmap("" + randomInterval(1000, 9999), width, height));
            }
        });



        Intent intent = getIntent();
        System.out.println();
    }

    private int randomInterval(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

}
