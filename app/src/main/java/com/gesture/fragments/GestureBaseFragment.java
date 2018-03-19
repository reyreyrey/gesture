package com.gesture.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.DialogFragment2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.gesture.R;
import com.gesture.helpers.GestureSetting;
import com.gesture.listeners.GestureListener;
import com.gesture.views.Lock9View;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import static com.gesture.helpers.GestureConst.DISMISS_GESTURE;

/**
 * Created by Rea.X on 2017/1/25.
 */

public abstract class GestureBaseFragment extends DialogFragment2 {

    protected GestureListener listener;
    protected Animation sharkAnim;
    protected int toolbarColor;
    protected int toolbarBackground;
    protected int toolbarTextcolor;
    protected View view_content;
    protected Lock9View lock_9_view;

    protected View contentView;
    protected GestureSetting setting;


    protected abstract
    @LayoutRes
    int getContentView();

    protected abstract void init();

    public void setListener(GestureListener listener) {
        this.listener = listener;
    }

    public void setGestureSetting(GestureSetting setting) {
        this.setting = setting;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(getContentView(), null);
        sharkAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_shark);
        view_content = $(R.id.view_content);
        lock_9_view = $(R.id.lock_9_view);

        if (setting != null) {
            if (lock_9_view != null)
                initLockView();
            if (view_content != null && setting.getColorPrimary() != 0) {
                view_content.setBackgroundColor(setting.getColorPrimary());
            }
            toolbarColor = setting.getToolbarColor() == 0 ? R.color.colorPrimary : setting.getToolbarColor();
            toolbarBackground = setting.getToolbarBackground();
            toolbarTextcolor = setting.getToolbarTextColor() == 0 ? Color.WHITE : setting.getToolbarTextColor();
        }

        EventBus.getDefault().register(this);
        init();
        return contentView;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_NoTitleBar);
    }

    private void initLockView() {
        lock_9_view.setNodeSrc(getResources().getDrawable(setting.getNodeNomalDrawable()));
        lock_9_view.setNodeOnSrc(getResources().getDrawable(setting.getNodeOnDrawable()));
        lock_9_view.setLineColor(setting.getLineColor());
        lock_9_view.setLineWidth(setting.getLineWidth());
        lock_9_view.setEnableVibrate(setting.enableVibrate());
        int anim = setting.nodeAnimation();
        if(anim != 0){
            lock_9_view.setNodeOnAnim(anim);
        }

    }

    protected <T extends View> T $(@IdRes int id) {
        return (T) contentView.findViewById(id);
    }

    protected void dismissDialog(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null && isAdded())
                    dismissAllowingStateLoss();
            }
        }, 500);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscriber(tag = DISMISS_GESTURE)
    public void dismissGesture(String s){
        dismissAllowingStateLoss();
    }

}
