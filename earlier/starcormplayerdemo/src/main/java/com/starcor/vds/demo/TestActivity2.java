package com.starcor.vds.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;

/**
 * Copyright (C) 2015 北京视达科科技有限公司 <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  15-12-11 下午2:53 <br>
 * description:
 */

public class TestActivity2 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout root = new RelativeLayout(this);
        root.setBackgroundResource(R.drawable.main_bg);
        setContentView(root);
    }

}
