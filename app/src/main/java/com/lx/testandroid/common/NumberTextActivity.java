package com.lx.testandroid.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.lx.testandroid.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

/**
 * Created by xuanluo on 2016/12/30.
 */

public class NumberTextActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.number);

        NumberTextView tv = (NumberTextView) findViewById(R.id.num);
        tv.setNum(0, 48546);

        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) DateFormat.getDateFormat(this);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        TextView tvDate = (TextView) findViewById(R.id.date_text);
        tvDate.setText(java.text.DateFormat.getDateInstance().format(new Date()));
    }

}
