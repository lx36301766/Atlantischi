package com.lx.testandroid;

import com.lx.testandroid.common.ClassLoaderActivity;
import com.lx.testandroid.market.MarketActivity;
import com.lx.testandroid.common.NumberTextActivity;
import com.lx.testandroid.log.KLogTestActivity;
import com.lx.testandroid.recyclerview.FragmentTestActivity;
import com.lx.testandroid.study.realm.RealmActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.root_main);

        addText(linearLayout, ClassLoaderActivity.class);
        addText(linearLayout, MarketActivity.class);
        addText(linearLayout, FragmentTestActivity.class);
        addText(linearLayout, KLogTestActivity.class);
        addText(linearLayout, NumberTextActivity.class);
        addText(linearLayout, RealmActivity.class);
    }

    private void addText(ViewGroup root, final Class<? extends Activity> clazz) {
        clazz.getSimpleName();
        TextView tv = new Button(this);
        tv.setPadding(0, 10, 0, 10);
//        tv.setBackgroundColor(Color.RED);
        tv.setGravity(Gravity.CENTER);
        tv.setText(clazz.getSimpleName());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, clazz);
                startActivity(intent);
            }
        });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = 10;
        lp.bottomMargin = 10;
        root.addView(tv, lp);
    }


}
