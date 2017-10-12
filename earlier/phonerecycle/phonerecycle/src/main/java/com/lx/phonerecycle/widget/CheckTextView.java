package com.lx.phonerecycle.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lx.phonerecycle.R;
import com.lx.phonerecycle.tools.UITools;

/**
 * Copyright (C) 2014 hx <br>
 * All rights reserved. <br>
 * author:  xiang.huang <br>
 * date:  2014/8/1 10:27 <br>
 * description:
 */
public class CheckTextView extends TextView{

    private String name;

    public CheckTextView(Context context) {
        super(context);
        initViews();
    }

    public CheckTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        textParams.setMargins(UITools.XH(5), 0, UITools.XH(5), 0);
        this.setGravity(Gravity.CENTER);
        this.setTextColor(0xff000000);
        this.setTextSize(18);
        this.setLayoutParams(textParams);
        this.setSingleLine();
        this.setPadding(15, 5, 15, 5);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked){
        isChecked = checked;
        if (checked){
            this.setBackgroundResource(R.drawable.shape_filter_check_circular_bg);
            this.setTextColor(0xffffffff);
        }else{
            this.setBackgroundResource(0);
            this.setTextColor(0xff000000);
        }
    }

}
