package com.gesture.listeners;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.gesture.fingerPrinter.FingerHelper;
import com.gesture.helpers.GestureHelper;
import com.gesture.helpers.GestureSetting;
import com.gesture.helpers.Helper;
import com.rea.commonUtils.common.CommonUtils;
import com.rea.commonUtils.log.LogUtil;


/**
 * author: Rea.X
 * date: 2017/3/10.
 */

public class GestureActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private GestureListener listener;
    private GestureSetting gestureSetting;
    private Class<? extends Activity> shouldNotOpenGestureActivity;
    private Class<? extends Activity> limitNumGotoActivity;

    private boolean isAppInTheForeground = false;

    public GestureActivityLifecycleCallbacks(GestureListener listener, GestureSetting gestureSetting, Class<? extends Activity> shouldNotOpenGestureActivity) {
        this.gestureSetting = gestureSetting;
        this.listener = listener;
        this.shouldNotOpenGestureActivity = shouldNotOpenGestureActivity;
    }

    public GestureActivityLifecycleCallbacks(GestureListener listener, GestureSetting gestureSetting, Class<? extends Activity> shouldNotOpenGestureActivity, Class<? extends Activity> limitNumGotoActivity) {
        this.gestureSetting = gestureSetting;
        this.listener = listener;
        this.shouldNotOpenGestureActivity = shouldNotOpenGestureActivity;
        this.limitNumGotoActivity = limitNumGotoActivity;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        LogUtil.e(activity + "------>onActivityResumed");
        if (!GestureHelper.hasOpengenture(activity)) return;
        if(!Helper.needShowGesture){
            isAppInTheForeground = CommonUtils.isAppInTheForeground(activity.getApplicationContext());
            LogUtil.d("test:::1:::"+Helper.needShowGesture+"    "+isAppInTheForeground);
            Helper.needShowGesture = true;
            return;
        }
        if (shouldNotOpenGestureActivity != null && activity.getLocalClassName().contains(shouldNotOpenGestureActivity.getSimpleName()))
            return;
        if (isAppInTheForeground) return;
        if (gestureSetting.openGestureIfLogin() && !gestureSetting.isLogin()) return;
        if (activity instanceof FragmentActivity) {
            if (GestureHelper.hasOpengenture(activity)) {
                if (Helper.isShouldNotShowGesture()) {
                    Helper.shouldNotShowGestureUsed();
                    return;
                }
                if (gestureSetting.hasOpenFingerPrinter() && FingerHelper.shouldUseFingerPrinter(activity)) {
                    Helper.openFingerPrinter((FragmentActivity) activity, "nomal", gestureSetting, listener);
                } else {
                    int num = GestureHelper.getLimitNumber(activity);
                    if(num <= 0){
                        if (limitNumGotoActivity == null) {
                            Helper.showPasswordFragment(((FragmentActivity) activity).getSupportFragmentManager(), "1", gestureSetting, listener, false);
                        } else {
                            activity.startActivity(new Intent(activity, limitNumGotoActivity));
                        }
                    }else{
                        Helper.openGesture((FragmentActivity) activity, "nomal", gestureSetting, listener);
                    }
                }
                isAppInTheForeground = true;
                return;
            }
        }
        isAppInTheForeground = CommonUtils.isAppInTheForeground(activity.getApplicationContext());
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(final Activity activity) {
        isAppInTheForeground = CommonUtils.isAppInTheForeground(activity.getApplicationContext());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        isAppInTheForeground = CommonUtils.isAppInTheForeground(activity.getApplicationContext());
    }
}
