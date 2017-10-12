package com.lx.phonerecycle.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lx.phonerecycle.R;
import com.lx.phonerecycle.gsonbean.N4A1_GetOrderType;
import com.lx.phonerecycle.tools.Tools;

import java.util.Date;
import java.util.List;

/**
 * Copyright (C) 2014 hx <br>
 * All rights reserved. <br>
 * author:  xiang.huang <br>
 * date:  2014/7/20 16:25 <br>
 * description:
 */
public class CommonRadioView extends LinearLayout {

    public CommonRadioView(Context context) {
        super(context);
    }

    public CommonRadioView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void upDataUI(List<N4A1_GetOrderType.Data> data) {
        removeAllViews();
        for (int i = 0; i < data.size(); i++) {
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Tools.dip2px(getContext(), 40));
            lp.weight = 1;
            StatusTextView textView = new StatusTextView(getContext());
            textView.setPadding(Tools.dip2px(getContext(), 8),0,Tools.dip2px(getContext(), 8),0);
            textView.setGravity(Gravity.CENTER);
            textView.setText(data.get(i).title);
            textView.setStatus(data.get(i).status);
            textView.setTextColor(getResources().getColorStateList(R.drawable.selector_common_item_text_color));
            if (0 == i) {
                textView.setBackgroundResource(R.drawable.selector_common_left);
            } else if (data.size() - 1 == i) {
                textView.setBackgroundResource(R.drawable.selector_common_right);
            } else {
                textView.setBackgroundResource(R.drawable.selector_common_center);
            }
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int childCount = getChildCount();
                    for (int j = 0; j < childCount; j++) {
                        getChildAt(j).setSelected(false);
                        v.setSelected(true);
                    }
                    if (onItemSelectedListener != null) {
                        onItemSelectedListener.onItemSelected(((StatusTextView)v).getStatus());
                    }
                }
            });
            addView(textView, lp);
        }
        getChildAt(0).performClick();
    }

    private class StatusTextView extends TextView {
        private String status;
        public StatusTextView(Context context) {
            super(context);
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    private OnItemSelectedListener onItemSelectedListener;

    public interface OnItemSelectedListener {
        void onItemSelected(String status);
    }

}
