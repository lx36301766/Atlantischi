package com.lx.phonerecycle;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.lx.phonerecycle.gsonbean.N3A10_RedemptionSubmit;
import com.lx.phonerecycle.tools.Tools;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-8-4 下午11:34 <br>
 * description:
 */

@ContentView(R.layout.activity_order_pay)
public class OrderPayActivity extends BaseActivity {

    @InjectView(R.id.redem_phone_text_info) private TextView redemPhoneTextInfo;
    @InjectView(R.id.redem_phone_price)     private TextView redemPhonePrice;
    @InjectView(R.id.alipay)                private TextView alipayImage;

    @InjectExtra(value = OrderSuccessActivity.REDEMPTION_SUBMIT_RESULT, optional = true)
    private N3A10_RedemptionSubmit.Data redemptionSubmitResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String info = String.format("你的 %s%s 手机换购 %s%s 成功",
                redemptionSubmitResult.brand,redemptionSubmitResult.model,
                redemptionSubmitResult.new_brand,redemptionSubmitResult.new_model);
        redemPhoneTextInfo.setText(info);
        redemPhonePrice.setText("新机的价格是：" + redemptionSubmitResult.price);
        alipayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastHint("暂未实现");
            }
        });
    }

    @Override
    public void onBackPressed() {
        Tools.jumpActivity(mActivity, HomeActivity.class);
        finish();
    }
}
