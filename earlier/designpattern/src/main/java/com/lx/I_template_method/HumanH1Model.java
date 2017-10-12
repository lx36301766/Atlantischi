package com.lx.I_template_method;

/**
 * 悍马车是每个越野者的最爱，其中H1最接近军用系列
 */
public class HumanH1Model extends HummerModel {

    private boolean alarmFlag = false;

    @Override
    protected void alarm() {
        System.out.println("悍马H1鸣笛...");
    }

    @Override
    protected void engineBoom() {
        System.out.println("悍马H1引擎声音是这样的...");
    }

    @Override
    protected void start() {
        System.out.println("悍马H1发动...");
    }

    @Override
    protected void stop() {
        System.out.println("悍马H1停车...");
    }

    @Override
    protected boolean isAlarm() {
        return this.alarmFlag;
    }

    // 要不要喇叭响，由客户来决定
    public void setAlarm(boolean isAlarm) {
        this.alarmFlag = isAlarm;
    }

}
