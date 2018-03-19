package com.gesture.helpers;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.gesture.fingerPrinter.FingerHelper;
import com.gesture.fragments.FingerPrinterFragment;
import com.gesture.fragments.GestureLockFragment;
import com.gesture.fragments.GestureSetFragment;
import com.gesture.fragments.GestureVerifiFragment;
import com.gesture.fragments.PasswordLockFragment;
import com.gesture.listeners.GestureActivityLifecycleCallbacks;
import com.gesture.listeners.GestureListener;

import org.simple.eventbus.EventBus;

import static com.gesture.helpers.GestureConst.DISMISS_GESTURE;

/**
 * Created by Rea.X on 2017/1/25.
 */

public class Helper {

    public static GestureListener gestureListener;
    //控制某次操作不需要显示手势
    private static boolean shouldNotShowGesture = false;

    public static void shouldNotShowGesture(){
        shouldNotShowGesture = true;
    }
    public static void shouldNotShowGestureUsed(){
        shouldNotShowGesture = false;
    }
    public static boolean isShouldNotShowGesture(){
        return shouldNotShowGesture;
    }


    /**
     * 打开指纹验证界面
     * @param context
     * @param flag
     * @param setting
     * @param listener
     * @return
     */
    public static FingerPrinterFragment openFingerPrinter(FragmentActivity context, String flag, GestureSetting setting, GestureListener listener) {
        if (!FingerHelper.shouldUseFingerPrinter(context)) {
            return null;
        }
        gestureListener = listener;
        EventBus.getDefault().post("", "closeFragment");
        FragmentManager fm = context.getSupportFragmentManager();
        FingerPrinterFragment fragment = FingerPrinterFragment.getInstant();
        if (fragment.getDialog() != null && fragment.getDialog().isShowing()) return fragment;
        fragment.setListener(listener);
        fragment.setGestureSetting(setting);
        fragment.setFlag(flag);
        fragment.show(fm, "dialog");
        return fragment;
    }


    /**
     * 打开手势验证设置界面
     * @param context
     * @param setting
     * @param listener
     * @return
     */
    public static GestureSetFragment openGestureSetting(Activity context, GestureSetting setting, GestureListener listener) {
        FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
        gestureListener = listener;
        GestureSetFragment fragment = GestureSetFragment.getInstant();
        if (fragment.getDialog() != null && fragment.getDialog().isShowing()) return fragment;
        fragment.setListener(listener);
        fragment.setGestureSetting(setting);
        fragment.show(fm, "dialog");
        return fragment;
    }

    /**
     * 打开手势验证界面
     * @param context
     * @param flag
     * @param setting
     * @param listener
     * @return
     */
    public static GestureLockFragment openGesture(FragmentActivity context, String flag, GestureSetting setting, GestureListener listener) {
        FragmentManager fm = context.getSupportFragmentManager();
        gestureListener = listener;
        GestureLockFragment fragment = GestureLockFragment.getInstant();
        if (fragment.getDialog() != null && fragment.getDialog().isShowing()) return fragment;
        fragment.setListener(listener);
        fragment.setGestureSetting(setting);
        fragment.setFlag(flag);
        fragment.show(fm, "dialog");
        return fragment;
    }

    public static PasswordLockFragment showPasswordFragment(FragmentManager fm , String flag, GestureSetting setting, GestureListener listener, boolean showGesLock) {
        PasswordLockFragment fragment = PasswordLockFragment.getInstant();
        fragment.setListener(listener);
        fragment.setGestureSetting(setting);
        fragment.setFlag(flag);
        fragment.setshowGestureLock(showGesLock);
        fragment.show(fm, "PasswordLockFragment");
        return fragment;
    }

    public static GestureVerifiFragment openGestureVerifi(FragmentActivity context, String flag, GestureSetting setting, GestureListener listener){
        FragmentManager fm = context.getSupportFragmentManager();
        gestureListener = listener;
        GestureVerifiFragment fragment = GestureVerifiFragment.getInstant();
        if (fragment.getDialog() != null && fragment.getDialog().isShowing()) return fragment;
        fragment.setListener(listener);
        fragment.setGestureSetting(setting);
        fragment.show(fm, "dialog");
        return fragment;
    }

    public static volatile boolean needShowGesture = true;
    public static void dismiss(){
        needShowGesture = false;
        EventBus.getDefault().post("", DISMISS_GESTURE);
    }

//
    /**
     * 初始化
     * @param application
     * @param listener
     * @param gestureSetting
     * @param shouldOpenGestureActivity
     */
    public static void init(Application application, GestureListener listener, GestureSetting gestureSetting, Class<? extends Activity> shouldOpenGestureActivity) {
        gestureListener = listener;
        application.registerActivityLifecycleCallbacks(new GestureActivityLifecycleCallbacks(listener, gestureSetting, shouldOpenGestureActivity));
    }

    public static void init(Application application, GestureListener listener, GestureSetting gestureSetting, Class<? extends Activity> shouldOpenGestureActivity, Class<? extends Activity> limitNumGotoActivity) {
        gestureListener = listener;
        application.registerActivityLifecycleCallbacks(new GestureActivityLifecycleCallbacks(listener, gestureSetting, shouldOpenGestureActivity, limitNumGotoActivity));
    }

}
