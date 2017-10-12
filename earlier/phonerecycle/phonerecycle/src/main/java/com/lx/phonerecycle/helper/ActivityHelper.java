package com.lx.phonerecycle.helper;

import android.app.Activity;
import android.os.Process;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-27 下午2:21 <br>
 * description:
 */

public class ActivityHelper {

    private List<Activity> activities = Lists.newArrayList();

    private ActivityHelper(){
    }

    private static ActivityHelper activityHelper = new ActivityHelper();

    public static ActivityHelper getInstance() {
        return activityHelper;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public void endApp() {
        for (Activity activity : activities) {
            activity.finish();
        }
        Process.killProcess(Process.myPid());
    }

}
