package com.gesture.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.DrawableRes;

import com.gesture.R;
import com.rea.commonUtils.common.MD5;

/**
 * Created by Rea.X on 2017/1/24.
 * 手势解锁工具类
 */

public class GestureHelper {
    /**
     * 最多重试的次数
     */
    public static final int MAX_TRY_NUMBER = 5;

    public static final int MAX_PWD_NUMBER = 3;
    /**
     * 最少需要连接的点
     */
    public static final int MIN_LINE_NUM = 4;

    private static SharedPreferences getPreferences(Context context) {
        context = context.getApplicationContext();
        return context.getSharedPreferences("gestures", 0);
    }

    private static void saveString(Context context, String key, String value) {
        getPreferences(context).edit().putString(key, value).commit();
    }

    private static void saveBoolean(Context context, String key, boolean value) {
        getPreferences(context).edit().putBoolean(key, value).commit();
    }

    private static void saveInteger(Context context, String key, int value) {
        getPreferences(context).edit().putInt(key, value).commit();
    }

    public static void reset(Context context) {
        context = context.getApplicationContext();
        setLimitNumber(context, MAX_TRY_NUMBER);
        setPwdLimitNumber(context, MAX_PWD_NUMBER);
    }

    /**
     * 设置是否开启手势密码验证
     *
     * @param context
     * @param hasOpen
     */
    public static void setHasOpenGesture(Context context, boolean hasOpen) {
        Helper.gestureListener.toggleGestureStatus(hasOpen);
        saveBoolean(context, "openGesture", hasOpen);
    }

    /**
     * 获取是否开启手势密码验证
     *
     * @param context
     * @return
     */
    public static boolean hasOpengenture(Context context) {
        return getPreferences(context).getBoolean("openGesture", true);
    }

    /**
     * 设置剩余的次数
     *
     * @param context
     * @param number
     */
    public static void setLimitNumber(Context context, int number) {
        saveInteger(context, "limitNumber", number);
    }

    /**
     * 获取剩余的次数
     *
     * @param context
     * @return
     */
    public static int getLimitNumber(Context context) {
        return getPreferences(context).getInt("limitNumber", MAX_TRY_NUMBER);
    }

    /**
     * 设置手势解锁密码
     *
     * @param context
     * @param password
     */
    public static void setGesturePassword(Context context, String password) {
        saveString(context, "password", password);
    }

    /**
     * 获取手势解锁密码
     *
     * @param context
     * @return
     */
    public static String getGesturePassword(Context context) {
        return getPreferences(context).getString("password", "");
    }

    /**
     * 设置手势是否是第一次运行
     *
     * @param context
     * @param isFrist
     */
    public static void setIsFrist(Context context, boolean isFrist) {
        saveBoolean(context, "frist", isFrist);
    }

    /**
     * 获取手势是否是第一次运行
     *
     * @param context
     * @return
     */
    public static boolean isFrist(Context context) {
        return getPreferences(context).getBoolean("frist", true);
    }


    /**
     * 设置剩余的次数
     *
     * @param context
     * @param number
     */
    public static void setPwdLimitNumber(Context context, int number) {
        saveInteger(context, "pwdlimitNumber", number);
    }

    /**
     * 获取剩余的次数
     *
     * @param context
     * @return
     */
    public static int getPwdLimitNumber(Context context) {
        return getPreferences(context).getInt("pwdlimitNumber", MAX_PWD_NUMBER);
    }


    public static void saveShowGestureLine(Context context, boolean isShow) {
        saveBoolean(context, "ShowGestureLine", isShow);
    }

    public static boolean isShowGestureLine(Context context) {
        return getPreferences(context).getBoolean("ShowGestureLine", true);
    }

//    public static void saveLoginPwd(Context context, String pwd) {
//        context = context.getApplicationContext();
//        SharedPreferences sharePreferences = context.getSharedPreferences("config", 0);
//        pwd = MD5.md5(pwd + GestureConst.PASSWORD_LAST);
//        sharePreferences.edit().putString("password", pwd).commit();
//
//    }
//
//    public static String getLoginPwd(Context context) {
//        context = context.getApplicationContext();
//        SharedPreferences sharePreferences = context.getSharedPreferences("config", 0);
//        return sharePreferences.getString("password", "");
//    }
}
