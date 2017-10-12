package com.atway.webshell.utils;

/**
 * Copyright (C) 2015 北京视达科科技有限公司 <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  15-12-23 下午9:08 <br>
 * description:
 */

public class TimeUtils {

    public static String timeInt2String(int time) {
        int sec = time % 60;
        int min = time / 60 % 60;
        int hour = time / 60 / 60;
        return createDigitsString(hour, 2)
                + ":" + createDigitsString(min, 2)
                + ":" + createDigitsString(sec, 2);
    }

    public static String createDigitsString(String digit, int num) {
        StringBuilder builder = new StringBuilder(digit);
        return (digit.length() < num) ? createDigitsString(builder.insert(0, '0').toString(), num) : builder.toString();
    }

    public static String createDigitsString(int digit, int num) {
        return createDigitsString(String.valueOf(digit), num);
    }

}
