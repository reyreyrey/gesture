package com.gesture.helpers;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.AnimRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import com.gesture.R;

import java.io.Serializable;

/**
 * Created by Rea.X on 2017/1/26.
 */

public abstract class GestureSetting implements Cloneable{
    /**
     * 设置正常状态下点的图片（第一次设置手势时上面的图片）
     *
     * @return
     */
    public
    @DrawableRes
    int getNodeNomalDrawable() {
        return R.drawable.node_small_normal;
    }

    /**
     * 是否开启指纹解锁
     *
     * @return
     */
    public boolean hasOpenFingerPrinter() {
        return true;
    }

    /**
     * 设置选中状态下点的图片（第一次设置手势时上面的图片）
     *
     * @return
     */
    public
    @DrawableRes
    int getNodeOnDrawable() {
        return R.drawable.node_small_active;
    }

    /**
     * 设置线的宽度
     *
     * @return
     */
    public int getLineWidth() {
        return 4;
    }

    /**
     * 设置界面颜色
     *
     * @return
     */
    public int getColorPrimary() {
        return Color.parseColor("#2f3d4c");
    }

    /**
     * 设置可以点击的文字颜色
     *
     * @return
     */
    public int getLinkTextColor() {
        return Color.parseColor("#ffffff");
    }

    /**
     * 设置正常文字的颜色
     *
     * @return
     */
    public int getNomalTextColor() {
        return Color.parseColor("#cdcdcd");
    }

    /**
     * 设置手势解锁未选中的图片
     *
     * @return
     */
    public int getShowGestureViewNomal() {
        return R.mipmap.bg_choice_join_n;
    }

    /**
     * 设置手势解锁选中的图片
     *
     * @return
     */
    public int getShowGestureViewChoice() {
        return R.mipmap.bg_choice_join_s;
    }


    /**
     * 是否震动
     *
     * @return
     */
    public boolean enableVibrate() {
        return true;
    }

    /**
     * 获取连线的颜色
     *
     * @return
     */
    public int getLineColor() {
        return 0;
    }

    /**
     * 获取主题颜色,不设置的话，默认是#C63630 红色
     *
     * @return
     */
    public int getToolbarColor() {
        return Color.parseColor("#C63630");
    }

    /**
     * 获取toolbar标题文字的颜色，默认是白色
     *
     * @return
     */
    public int getToolbarTextColor() {
        return Color.WHITE;
    }


    /**
     * 设置toolbar背景
     *
     * @return
     */
    public int getToolbarBackground() {
        return 0;
    }

    /**
     * 设置解锁界面图标
     *
     * @return
     */
    public abstract
    @DrawableRes
    int getIcon();

    /**
     * 是否已经登录了
     *
     * @return
     */
    public abstract boolean isLogin();


    /**
     * 设置登录界面Activity
     *
     * @return
     */
    public abstract Class<? extends Activity> getLoginActivity();

    /**
     * 点击返回键是否可以取消
     *
     * @return
     */
    public abstract boolean enableDismiss();

    /**
     * 设置手势密码的时候是否显示“暂不设置”
     *
     * @return
     */
    public boolean showSkipGesture() {
        return true;
    }

    /**
     * 只在登录之后开启手势（默认）
     *
     * @return
     */
    public boolean openGestureIfLogin() {
        return true;
    }

    /**
     * 获取登录密码
     *
     * @return
     */
    public abstract String getPassword();

    public abstract boolean onCheckPwd(String inputPwd);

    public int getNavigationIcon() {
        return R.drawable.btn_icon_return_selector;
    }

    ;

    //是否展示用户名
    public boolean showUser() {
        return false;
    }

    //showUser为true时必须设置
    public String getLoginName() {
        return null;
    }

    /**
     * 眼睛状态是否是闭上的
     * A02默认是睁开的  A03默认是闭上的
     *
     * @return
     */
    public boolean eyeIconIsClosedDefault() {
        return false;
    }

    public
    @AnimRes
    int nodeAnimation() {
        return R.anim.node_on_1;
    }
}
