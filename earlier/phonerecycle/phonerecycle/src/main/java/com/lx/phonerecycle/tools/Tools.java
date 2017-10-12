package com.lx.phonerecycle.tools;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.*;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.text.style.ForegroundColorSpan;
import android.util.Pair;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@SuppressWarnings("all")
public class Tools {

    public static final String SEPARATOR = ";";
    private static CharSequence oldMsg;
    private static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    private Tools() {
    }

    public static int getResourceByName(Context context, String className, String name) {
        String packageName = context.getPackageName();
        Class r = null;
        int id = 0;
        try {
            r = Class.forName(packageName + ".R");
            Class[] classes = r.getClasses();
            Class desireClass = null;
            for (int i = 0; i < classes.length; ++i) {
                if (classes[i].getName().split("\\$")[1].equals(className)) {
                    desireClass = classes[i];
                    break;
                }
            }
            if (desireClass != null)
                id = desireClass.getField(name).getInt(desireClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static Calendar getCalendar() {
//        String date = DvbApi.getInstance().dvb_get_property("dvb_get_systemtime_date", new Propertys());
//        String time = DvbApi.getInstance().dvb_get_property("dvb_get_systemtime_time", new Propertys());
//        int year = Integer.parseInt(date.split("-")[0]);
//        int mouth = Integer.parseInt(date.split("-")[1]);
//        int day = Integer.parseInt(date.split("-")[2]);
//        int hourOfDay = Integer.parseInt(time.split(":")[0]);
//        int minute = Integer.parseInt(time.split(":")[1]);
//        int second = Integer.parseInt(time.split(":")[2]);
        Calendar calendar = Calendar.getInstance();
//        calendar.set(year, mouth - 1, day, hourOfDay, minute, second);
        return calendar;
    }

    public static int computeTimeLengthInMinute(String start, String end) {
        int startInt = getTimeInMinute(start);
        int endInt = getTimeInMinute(end);
        int length = endInt - startInt;
        final int DAY_IN_MINUTE = 24 * 60;
        if (endInt < startInt) {
            length = DAY_IN_MINUTE - (startInt - endInt);
        }
        return Math.max(0, length);
    }

    public static int getTimeInMinute(String ts) {
        String[] strings = ts.split(":");
        int time = 0;
        for (int i = 0; i < strings.length; i++) {
            int n = Integer.parseInt(strings[i]);
            time += n * Math.pow(60, strings.length - i - 1);
        }
        return time;
    }

    public static int getTimeInSecond(String time) {
        String[] strings = time.split(":");
        int[] ints = new int[strings.length];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = Integer.parseInt(strings[i]);
        }
        return ints[0] * 60 * 60 + ints[1] * 60 + ints[2];
    }

    public static int computeTimeLag(String start, String end) {
        int timeLag = getTimeInSecond(end) - getTimeInSecond(start);
        if (timeLag < 0) {
            timeLag = getTimeInSecond("24:00:00") + timeLag;
        }
        return timeLag;
    }

    public static int getTimePercent(String startTime, String endTime) {
        int hour = Tools.getCalendar().get(Calendar.HOUR_OF_DAY);
        int minutes = Tools.getCalendar().get(Calendar.MINUTE);
        int seconds = Tools.getCalendar().get(Calendar.SECOND);
        String curTime = hour + ":" + minutes + ":" + seconds;
        float programTimeLength = Tools.computeTimeLag(startTime, endTime);
        float playedTimeLength = Tools.computeTimeLag(startTime, curTime);
        int progress = (int) ((playedTimeLength / programTimeLength) * 100);
        return progress;
    }

    public static String timeInt2String(int time) {
        int sec = time % 60;
        int min = time / 60 % 60;
        int hour = time / 60 / 60;
//        return String.format("%s:%s:%s", createDigitsString(hour, 2),
//                createDigitsString(min, 2), createDigitsString(sec, 2));
        return createDigitsString(hour, 2)
                + ":" + createDigitsString(min, 2)
                + ":" + createDigitsString(sec, 2);
    }

    public static int getCurTimeInSecond() {
        return getTimeInSecond(getCurrentTime());
    }

    public static String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(getCalendar().getTime());
    }

    public static float px2DpF(int dp, Resources resources) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

    public static int px2Dp(int dp, Resources resources) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

    public static float px2DpF(Context context, int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int px2Dp(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int clampNum(int num, int max, int min) {
        if (num < min) {
            return min;
        } else if (num > max) {
            return max;
        } else {
            return num;
        }
    }

    public static int loopNum(int num, int max, int min) {
        if (num < min) {
            return max;
        } else if (num > max) {
            return min;
        } else {
            return num;
        }
    }

    public static void jumpActivity(Context context, Class clazz, Pair<String, ?>... intentArgs) {
        jumpActivityWithFullName(context, clazz.getName(), intentArgs);
    }

    public static void jumpActivity(Context context, String name, Pair<String, ?>... intentArgs) {
        jumpActivityWithFullName(context, context.getPackageName() + "." + name, intentArgs);
    }

    private static final boolean USE_ANDROID_ANNOTATIONS = false;

    public static void jumpActivityWithFullName(Context context, String fullName, Pair<String, ?>... intentArgs) {
        if (USE_ANDROID_ANNOTATIONS) {
            if (!fullName.contains("_")) {
                fullName = fullName + "_";
            }
        }
        Intent intent = new Intent();
        intent.setClassName(context, fullName);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        for (Pair<String, ?> arg : intentArgs) {
            parseIntentArgs(context, intent, arg);
        }
        context.startActivity(intent);
    }

    private static void parseIntentArgs(Context context, Intent intent, Pair<String, ?> intentArgs) {
        String key = intentArgs.first;
        Object value = intentArgs.second;
        if (value instanceof Bundle) {
            intent.putExtra(key, (Bundle) value);
        } else if (value instanceof Boolean) {
            intent.putExtra(key, ((Boolean) value).booleanValue());
        } else if (value instanceof Byte) {
            intent.putExtra(key, ((Byte) value).byteValue());
        } else if (value instanceof Character) {
            intent.putExtra(key, ((Character) value).charValue());
        } else if (value instanceof Short) {
            intent.putExtra(key, ((Short) value).shortValue());
        } else if (value instanceof Integer) {
            intent.putExtra(key, ((Integer) value).intValue());
        } else if (value instanceof Long) {
            intent.putExtra(key, ((Long) value).longValue());
        } else if (value instanceof Float) {
            intent.putExtra(key, ((Float) value).floatValue());
        } else if (value instanceof Double) {
            intent.putExtra(key, ((Double) value).doubleValue());
        } else if (value instanceof String) {
            intent.putExtra(key, (String) value);
        } else if (value instanceof CharSequence) {
            intent.putExtra(key, (CharSequence) value);
        } else if (value instanceof Parcelable) {
            intent.putExtra(key, (Parcelable) value);
        } else if (value instanceof Parcelable[]) {
            intent.putExtra(key, (Parcelable[]) value);
        } else if (value instanceof ArrayList<?>) {
            ArrayList<?> val = (ArrayList<?>) value;
            if (val.size() > 0) {
                if (val.get(0) instanceof Parcelable) {
                    intent.putParcelableArrayListExtra(key, (ArrayList) val);
                } else if (val.get(0) instanceof Integer) {
                    intent.putIntegerArrayListExtra(key, (ArrayList) val);
                } else if (val.get(0) instanceof String) {
                    intent.putStringArrayListExtra(key, (ArrayList) val);
                } else {
                    intent.putExtra(key, val);
                }
            } else {
                intent.putExtra(key, val);
            }
        } else if (value instanceof boolean[]) {
            intent.putExtra(key, (boolean[]) value);
        } else if (value instanceof byte[]) {
            intent.putExtra(key, (byte[]) value);
        } else if (value instanceof short[]) {
            intent.putExtra(key, (short[]) value);
        } else if (value instanceof char[]) {
            intent.putExtra(key, (char[]) value);
        } else if (value instanceof int[]) {
            intent.putExtra(key, (int[]) value);
        } else if (value instanceof long[]) {
            intent.putExtra(key, (long[]) value);
        } else if (value instanceof float[]) {
            intent.putExtra(key, (float[]) value);
        } else if (value instanceof double[]) {
            intent.putExtra(key, (double[]) value);
        } else if (value instanceof String[]) {
            intent.putExtra(key, (String[]) value);
        } else if (value instanceof CharSequence[]) {
            intent.putExtra(key, (CharSequence[]) value);
        } else if (value instanceof Serializable) {
            intent.putExtra(key, (Serializable) value);
        } else {
//            showToast(context, "unknown intent type:" + value.getClass().getName());
        }
    }

    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId));
    }

    public static void showToast(Context context, CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (text.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = text;
                toast.setText(text);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    public static SpannableString createColorString(String str, int color) {
        SpannableString spannable = new SpannableString(str);
        spannable.setSpan(new ForegroundColorSpan(color), 0, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    public static String createDigitsString(String digit, int num) {
        StringBuilder builder = new StringBuilder(digit);
        return (digit.length() < num) ? createDigitsString(builder.insert(0, '0').toString(), num) : builder.toString();
    }

    public static String createDigitsString(int digit, int num) {
        return createDigitsString(String.valueOf(digit), num);
    }

    public static String formatPlayTimeDigits(String time) {
        String[] nums = time.split(":");
        StringBuilder builder = new StringBuilder();
        builder.append(createDigitsString(nums[0], 2)).append(":");
        builder.append(createDigitsString(nums[1], 2)).append(":");
        builder.append(createDigitsString(nums[2], 2));
        return builder.toString();
    }

//    public static void showFragment(FragmentManager manager, BaseFragment fragment) {
//        if (manager == null || fragment == null) {
//            return;
//        }
//        FragmentTransaction ft = manager.beginTransaction();
//        ft.setCustomAnimations(fragment.getEnterAnimation(), fragment.getExitAnimation());
//        ft.show(fragment);
//        ft.commit();
//    }
//
//    public static void hideFragment(FragmentManager manager, BaseFragment fragment) {
//        if (manager == null || fragment == null) {
//            return;
//        }
//        FragmentTransaction ft = manager.beginTransaction();
//        ft.setCustomAnimations(fragment.getEnterAnimation(), fragment.getExitAnimation());
//        ft.hide(fragment);
//        ft.commit();
//    }

    public static void notifyAdapter(ListView listView) {
        if (listView != null && listView.getAdapter() instanceof BaseAdapter) {
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
    }

    public static <F extends Fragment> F replaceFragment(FragmentManager manager, Class<F> clazz) {
        return replaceFragment(manager, 0, clazz);
    }

    public static <F extends Fragment> F replaceFragment(FragmentManager manager, int containerViewId, Class<F> clazz) {
        return replaceFragment(manager, containerViewId, clazz, null);
    }

    @SuppressWarnings("unchecked")
    public static <F extends Fragment> F replaceFragment(FragmentManager manager, int containerViewId, Class<F> clazz, String tag) {
        Fragment fragment = null;
        try {
            fragment = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FragmentTransaction t = manager.beginTransaction();
        t.replace(containerViewId, fragment, tag);
        t.addToBackStack(null);
        t.commit();
        return (F) fragment;
    }

    public static <F extends Fragment> F addFragment(FragmentManager manager, Class<F> clazz) {
        return addFragment(manager, 0, clazz);
    }

    public static <F extends Fragment> F addFragment(FragmentManager manager, int containerViewId, Class<F> clazz) {
        return addFragment(manager, containerViewId, clazz, null);
    }

    @SuppressWarnings("unchecked")
    public static <F extends Fragment> F addFragment(FragmentManager manager, int containerViewId, Class<F> clazz, String tag) {
        Fragment fragment = null;
        try {
            fragment = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FragmentTransaction t = manager.beginTransaction();
        t.add(containerViewId, fragment, tag);
        t.addToBackStack(null);
        t.commit();
        return (F) fragment;
    }

    public static String MillisecToYMD(long millis) {
//        if (milli != null && milli.length() > 13) {
//            milli = milli.substring(0, 13);
//        }
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        calendar.setTimeInMillis(millis);
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1; // time, month 1~12
        int year = calendar.get(Calendar.YEAR);
        if (month < 10) {
            return year + ":0" + month;
        }

        return year + ":" + month + ":" + day;
    }

    public static String MillisecToTimeStr(long millis) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(millis);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // time, month 1~12
        int day = calendar.get(Calendar.DATE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        StringBuilder builder = new StringBuilder();
        builder.append(year).append("-");
        builder.append(createDigitsString(month, 2)).append("-");
        builder.append(createDigitsString(day, 2)).append(" ");
        builder.append(createDigitsString(hour, 2)).append(":");
        builder.append(createDigitsString(minute, 2)).append(":");
        builder.append(createDigitsString(second, 2));
        return builder.toString();
    }

    /**
     * @param contentLength
     * @return
     * @see Formatter#formatFileSize(context, number)
     */
    public static String longSizeToStr(long contentLength) {
        float length = contentLength;
        String strLen;
        if (length >= 1024 * 1024 * 1024) {
            length /= 1024 * 1024 * 1024;
            String s = String.valueOf(length);
            strLen = s.substring(0, s.length() > 4 ? 4 : s.length()) + "GB";
            while (strLen.length() < 6)
                strLen = strLen.replace("GB", "0GB");
        } else if (length >= 1024 * 1024) {
            length /= 1024 * 1024;
            String s = String.valueOf(length);
            strLen = s.substring(0, s.length() > 4 ? 4 : s.length()) + "MB";
            while (strLen.length() < 6)
                strLen = strLen.replace("MB", "0MB");
        } else if (length >= 1024) {
            length /= 1024;
            String s = String.valueOf(length);
            strLen = s.substring(0, s.length() > 4 ? 4 : s.length()) + "KB";
            while (strLen.length() < 6)
                strLen = strLen.replace("KB", "0KB");
        } else {
            strLen = (int) (length) + "B";
        }
        for (int i = 0; i < strLen.length(); i++) {
            if (strLen.charAt(i) == '.' && i == strLen.length() - 3) {
                strLen = strLen.substring(0, i) + strLen.substring(i + 1);
                break;
            }
        }
        return strLen;
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static String objectArrayToString(Object[] strs) {
        if (strs == null) {
            return null;
        }
        return Joiner.on(SEPARATOR + " ").skipNulls().join(strs);
    }

//    public static List<String> stringToList(String str) {
//        if (str == null) {
//            return new ArrayList<String>();
//        }
//        return Lists.newArrayList(Splitter.on(SEPARATOR).trimResults().omitEmptyStrings().split(str));
//    }
//
    public static String[] stringToStringArray(String str) {
        if (str == null) {
            return new String[0];
        }
        ArrayList<String> strings = Lists.newArrayList(Splitter.on(SEPARATOR).trimResults().omitEmptyStrings().split(str));
        return strings.toArray(new String[strings.size()]);
    }

    public static String intArrayToString(int[] strs) {
        StringBuilder sb = new StringBuilder();
        for (int str : strs) {
            sb.append(str);
            sb.append(Tools.SEPARATOR);
        }
        return sb.toString();
    }

//    public static String[] intArrayToStringArray(int[] strs) {
//        return stringToStringArray(intArrayToString(strs));
//    }

    public static <T> T[] reverseArray(T[] objects) {
        List<T> objectList = Arrays.asList(objects);
        Collections.reverse(objectList);
        return objectList.toArray(objects);
    }

    /**
     * 此方法将一个字符串中包含有汉字拼音转化成首字母
     */
    public static String ChineseToPinyinInitial(String str) {
        if (null == str || "".equals(str.trim()))
            return null;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (isChinese(str.charAt(i))) {
                builder.append(toPinYin(new String(new char[]{str.charAt(i)})).charAt(0));
            } else {
                builder.append(str.charAt(i));
            }
        }
        return builder.toString();
    }

    /**
     * 此方法可以将一个字符串中包含有汉字的字符转化成拼音，可以同时混合输入其他非汉字字符
     */
    public static String ChineseToPinyin(String str) {
        if (null == str || "".equals(str.trim()))
            return null;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (isChinese(str.charAt(i))) {
                builder.append(toPinYin(new String(new char[]{str.charAt(i)})));
            } else {
                builder.append(str.charAt(i));
            }
        }
        return builder.toString();
    }

    /**
     * 此方法只能把纯汉字转成拼音，如有其他非汉字字符则会报错
     */
    public static String toPinYin(String hanzhis) {
        CharSequence s = hanzhis;

        char[] hanzhi = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            hanzhi[i] = s.charAt(i);
        }

        String[] t2 = new String[s.length()];
        /**
         * 设置输出格式
         */
        net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);

        int t0 = hanzhi.length;
        String py = "";
        try {
            for (char aT1 : hanzhi) {
                t2 = PinyinHelper.toHanyuPinyinStringArray(aT1, t3);
                py = py + t2[0];
            }
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return py.trim();
    }

    /**
     * 判断一个字符是否是汉字
     */
    public static boolean isChinese(char a) {
        int v = (int) a;
        return (v >= 0x4E00 && v <= 0x9FFF);
    }

    /**
     * 图片去色,返回灰度图片
     */
    public static Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayScale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayScale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayScale;
    }

    /**
     * long值转换成ip地址 方法表述
     */
    public static String long2Ip(long ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 根据子网掩码长度计算子网掩码
     */
    public static String prefixLengthToMaskString(int length) {
        if (length == 0) {
            return "0.0.0.0";
        } else if (length < 0 || length > 32) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        int mask = 0xffffffff << 32 - length;
        builder.append((mask >> 24) & 0xff).append(".");
        builder.append((mask >> 16) & 0xff).append(".");
        builder.append((mask >> 8) & 0xff).append(".");
        builder.append(mask & 0xff);
        return builder.toString();
    }

    /**
     * 根据子网掩码计算子网掩码长度
     */
    public static int maskStringToLength(String netMask) throws IllegalArgumentException {
        String[] items = netMask.split("\\.");
        Integer[] nums = {0, 128, 192, 224, 240, 248, 252, 254, 255};
        List<Integer> numList = Arrays.asList(nums);
        int length = 0;
        for (String str : items) {
            int num = Integer.parseInt(str);
            Preconditions.checkArgument(numList.contains(num), "错误的子网掩码数字: " + num);
            for (int i = 1; i <= 8; i++) {
                int b = num << i & 0xff;
                if (b == 0 && num != 0) {
                    length += i;
                    break;
                }
            }
        }
        return length;
    }

    public static boolean isLegitimateIP(Context context, String ip, String nullHint, String errorHint) {
        String ipRegex = "\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}";
        if (TextUtils.isEmpty(ip)) {
            showToast(context, nullHint);
            return false;
        }
        if (!Pattern.matches(ipRegex, ip)) {
            showToast(context, errorHint);
            return false;
        }
        return true;
    }

    public static boolean isLegitimateEmail(String email) {
        String emailRegex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return !TextUtils.isEmpty(email) && Pattern.matches(emailRegex, email);
    }

    public static boolean isLegitimatePhone(String phone) {
//        String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        String regex = "^[0]?1[4358]\\d{9}$";
        return !TextUtils.isEmpty(phone) && Pattern.matches(regex, phone);
    }

    public static String getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        System.out.println("availMem;" + mi.availMem);
        return Formatter.formatFileSize(context, mi.availMem);
    }

    public static String getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                XLog.i(str2, num + "\t");
            }
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return Formatter.formatFileSize(context, initial_memory);
    }

    public static boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static String getAvailableInternalMemorySize(Context context) {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(context, availableBlocks * blockSize);
    }

    public static String getTotalInternalMemorySize(Context context) {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(context, totalBlocks * blockSize);
    }

    public static String getAvailableExternalMemorySize(Context context) {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return Formatter.formatFileSize(context, availableBlocks * blockSize);
        } else {
            return "ERROR";
        }
    }

    public static String getTotalExternalMemorySize(Context context) {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return Formatter.formatFileSize(context, totalBlocks * blockSize);
        } else {
            return "ERROR";
        }
    }

    /**
     * 判断某一个应用程序是不是系统的应用程序，
     * 如果是返回true，否则返回false。
     */
    public static boolean isSystemApp(ApplicationInfo info) {
        //有些系统应用是可以更新的，如果用户自己下载了一个系统的应用来更新了原来的，它还是系统应用，这个就是判断这种情况的
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return false;
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {//判断是不是系统应用
            return false;
        }
        return true;
    }

    public static boolean isAppRunning(Context context, String pkgName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(pkgName) || info.baseActivity.getPackageName().equals(pkgName)) {
                isAppRunning = true;
                XLog.i("lx", info.topActivity.getPackageName() + " info.baseActivity.getPackageName()=" + info.baseActivity.getPackageName());
                break;
            }
        }
        return isAppRunning;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static <T extends View> T getViewByHolder(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    public static int computeInitialSampleSize(BitmapFactory.Options options,
                                               int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static final File convertBmpToFile(Context context, Bitmap bitmap) {
        try {
            File f = new File(context.getCacheDir(), "cacheImage.png");
            f.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90 /*ignored for PNG*/, bos);
            byte[] bitmapData = bos.toByteArray();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapData);
            return f;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveObjToFile(String path, Object object) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        File f = new File(path);
        try {
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            if (!f.exists()) {
                f.createNewFile();
            }
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);    //括号内参数为要保存java对象
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object readObjFromFile(String path) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        File f = new File(path);
        try {
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }

}
