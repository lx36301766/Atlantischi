package com.lx.phonerecycle.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
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
public class AssessArgsTextView extends TextView{

    private String parentId;
    private String childId;

    public AssessArgsTextView(Context context) {
        super(context);
        initViews();
    }

    public AssessArgsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        textParams.setMargins(UITools.XH(5), 0, UITools.XH(5), 0);
        setGravity(Gravity.CENTER);
        setTextColor(0xff000000);
        setTextSize(14);
        setLayoutParams(textParams);
//        setSingleLine();
        setPadding(15, 5, 15, 5);
        setBackgroundResource(R.drawable.shape_assess_args_text_bg);
//        setEllipsize(TextUtils.TruncateAt.END);
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public void setChecked(boolean checked){
        if (checked){
            setBackgroundResource(R.drawable.shape_assess_args_text_selected_bg);
            setTextColor(0xffffffff);
        }else{
            setBackgroundResource(R.drawable.shape_assess_args_text_bg);
            setTextColor(0xff000000);
        }
    }

}
