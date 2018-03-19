package com.gesture.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gesture.R;
import com.gesture.helpers.GestureHelper;
import com.gesture.helpers.Helper;
import com.gesture.helpers.Utils;
import com.gesture.views.Lock9View;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;


/**
 * Created by Rea.X on 2017/1/25.
 * 手势解锁界面
 */
public class GestureLockFragment extends GestureBaseFragment implements View.OnClickListener, Lock9View.CallBack {

    //logo
    private ImageView iv_01;
    //说明文字
    private TextView tv_gesture_text;
    //解锁图案View
    //密码解锁,忘记密码
    private TextView tv_password_lock, tv_password_forget;
    //
    //是否已经登录
    private boolean isLogin;
    //是否是第一次运行
    private boolean isFristLaunch;
    //设置的密码
    private String password;

    private GestureLockFragment fragment;

    private String flag;

    private static  GestureLockFragment fragmentq;
    public static GestureLockFragment getInstant(){
        if(fragmentq == null){
            fragmentq = new GestureLockFragment();
        }
        return fragmentq;
    }


    @Override
    protected int getContentView() {
        return R.layout.layout_gesture_lock;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!GestureHelper.hasOpengenture(getActivity())) {
            dismiss();
            return;
        }
        setCancelable(setting == null ? false : setting.enableDismiss());
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void init() {
        intViews();
        initData();
        checkFristLunch();
    }

    private void initData() {
        if (setting != null) {
            password = GestureHelper.getGesturePassword(getActivity());
            iv_01.setImageDrawable(setting.getIcon() == 0 ? Utils.getApplicationIcon(getActivity()) : getResources().getDrawable(setting.getIcon()));
            isLogin = setting.isLogin();
            isFristLaunch = GestureHelper.isFrist(getActivity());
            tv_password_lock.setVisibility(isLogin ? View.VISIBLE : View.INVISIBLE);
            tv_password_lock.setOnClickListener(this);
            tv_password_lock.setTextColor(setting.getLinkTextColor());
            tv_password_forget.setTextColor(setting.getLinkTextColor());
            tv_password_forget.setOnClickListener(this);
            lock_9_view.setCallBack(this);
            tv_gesture_text.setTextColor(setting.getNomalTextColor());
        }
    }

    private void checkFristLunch() {
        if (isFristLaunch) {
            if (setting != null) {
                Helper.openGestureSetting((FragmentActivity) getActivity(), setting, listener);
            }
            dismissDialog();
        }
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }


    private void intViews() {
        iv_01 = $(R.id.iv_01);
        tv_gesture_text = $(R.id.tv_gesture_text);
        tv_password_lock = $(R.id.tv_password_lock);
        tv_password_forget = $(R.id.tv_password_forget);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_password_forget) {
            if (isLogin) {
                //没有登录时跳转到密码解锁界面
                showPwdLock(true);
            } else if (listener != null){
                listener.onForgetPwd(this);
//                WebViewUtils.customer(getActivity());
            }
        } else if (view.getId() == R.id.tv_password_lock) {
            //跳转到密码解锁界面
            showPwdLock(true);
        }
    }

    private void showPwdLock(boolean showGesLock) {
        Helper.showPasswordFragment(getFragmentManager(), "1", setting, listener, showGesLock);
        dismissDialog();
    }

    @Override
    public void onFinish(String s) {
        if (s.equals(password)) {
            if (listener != null) {
                listener.onVerFinish(this, flag, true);
            }
            GestureHelper.setLimitNumber(getActivity(), GestureHelper.MAX_TRY_NUMBER);
        } else {
            if (listener != null) {
                listener.onVerFinish(this, flag, false);
            }
            int num = GestureHelper.getLimitNumber(getActivity());
            num--;
            GestureHelper.setLimitNumber(getActivity(), num);
            if (num <= 0) {
                showPwdLock(false);
                return;
            }
            tv_gesture_text.setTextColor(Color.parseColor("#A00720"));
            tv_gesture_text.setText(String.format(getString(R.string.ges_error), num));
            tv_gesture_text.startAnimation(sharkAnim);
        }
    }

    @Subscriber(tag = "closeFragment")
    public void closeFragment(String s) {
        fragment.dismissAllowingStateLoss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        fragmentq = null;
    }
}
