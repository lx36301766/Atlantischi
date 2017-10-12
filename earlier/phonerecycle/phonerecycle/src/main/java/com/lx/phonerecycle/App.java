package com.lx.phonerecycle;

import android.app.Application;
import com.lx.phonerecycle.helper.HtmlHelper;
import com.lx.phonerecycle.helper.LoginInfoHelper;
import com.lx.phonerecycle.tools.UITools;

/**
 * Created with IntelliJ IDEA.<br>
 * User: luo.xuan<br>
 * Date: 14-7-15<br>
 * Time: 下午11:46<br>
 */

public class App extends Application {

    private static final String TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        LoginInfoHelper.init(this);
        HtmlHelper.init(this);
        // 初始化UI工具
        UITools.init(this);
    }

}
