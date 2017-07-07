package com.lx.testandroid.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.lx.testandroid.R;
import com.lx.testandroid.testviews.PayCenterLeaveDialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.View;
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

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "<font color=\"#333333\">便宜不等人，此单可享最高</font><font "
                        + "color=\"#FE4070\">100000104.64元</font><font color=\"#333333\">减免</font>";
                CharSequence text = Html.fromHtml(str);
                PayCenterLeaveDialog.Builder builder = new PayCenterLeaveDialog.Builder(NumberTextActivity.this);
                builder.setContext(text)
                        .setLeftButton("遗憾放弃", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setRightButton("遗憾放弃", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();

            }
        });
    }

}
