package com.lx.testandroid.rx;

import androidx.annotation.NonNull;

/**
 * Created by haoyuew on 2017/6/30.
 */

public class LaunchImage implements Comparable<LaunchImage> {

    public String tag;

    public String showTimeBegin;

    public String showTimeEnd;

    public int showRule; //1：总是展示 2：根据show_rule_limit限制次数

    public int showRuleLimit;

    public int priority;

    public int showCount;


    public LaunchImage() {
    }

    @Override
    public int compareTo(@NonNull LaunchImage o) {
        return o.priority - priority;
    }

    @Override
    public String toString() {
        return "LaunchImage{" +
                "tag='" + tag + '\'' +
                ", showRule=" + showRule +
                ", showRuleLimit=" + showRuleLimit +
                ", priority=" + priority +
                ", showCount=" + showCount +
                '}';
    }
}
