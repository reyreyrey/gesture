package com.gesture.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gesture.R;
import com.gesture.helpers.GestureHelper;
import com.gesture.helpers.Utils;
import com.gesture.views.Lock9View;

/**
 * author: Rea.X
 * date: 2017/4/26.
 * 用于重置手势密码时验证
 */

public class GestureVerifiFragment extends GestureBaseFragment implements View.OnClickListener, Lock9View.CallBack{
    //toolbar
    private Toolbar toolbar;
    //标题栏
    private TextView tv_title;
    //说明文字
    private TextView tv_gesture_text;
    //密码验证
    private TextView tv_password_ver;
    //logo
    private ImageView iv_01;
    @Override
    protected int getContentView() {
        return R.layout.layout_gesture_ver;
    }

    private static  GestureVerifiFragment fragmentq;
    public static GestureVerifiFragment getInstant(){
        if(fragmentq == null){
            fragmentq = new GestureVerifiFragment();
        }
        return fragmentq;
    }

    @Override
    protected void init() {
        initView();
        initData();
    }
    private void initView() {
        iv_01 = $(R.id.iv_01);
        toolbar = $(R.id.toolbar);
        tv_title = $(R.id.tv_title);
        tv_gesture_text = $(R.id.tv_gesture_text);
        tv_password_ver = $(R.id.tv_password_ver);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    private void initData() {
        if (setting != null) {
            toolbar.setTitle("");
            if (toolbarBackground != 0) {
                toolbar.setBackgroundResource(toolbarBackground);
            } else {
                toolbar.setBackgroundColor(toolbarColor);
            }
            iv_01.setImageDrawable(setting.getIcon() == 0 ? Utils.getApplicationIcon(getActivity()) : getResources().getDrawable(setting.getIcon()));
            toolbar.setNavigationIcon(getResources().getDrawable(setting.getNavigationIcon()));
            tv_title.setTextColor(toolbarTextcolor);
            lock_9_view.setCallBack(this);
            tv_gesture_text.setTextColor(setting.getNomalTextColor());
            tv_password_ver.setTextColor(setting.getLinkTextColor());

            tv_password_ver.setOnClickListener(this);
        }
    }

    @Override
    public void onFinish(String password) {
        if (listener == null)
            return;
        if(password.equals(GestureHelper.getGesturePassword(getActivity()))){
            listener.onVerFinish(this, "", true);
//            Helper.openGestureSetting((AppCompatActivity) getActivity(), setting, listener);
        }else{
            listener.onVerFinish(this, "", false);
            tv_gesture_text.setTextColor(Color.parseColor("#A00720"));
            tv_gesture_text.setText(getString(R.string.ges_error1));
            tv_gesture_text.startAnimation(sharkAnim);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tv_password_ver){
            showPwdLock();
            return;
        }
    }

    private void showPwdLock() {
        PasswordLockFragment fragment = PasswordLockFragment.getInstant();
        fragment.setListener(listener);
        fragment.setGestureSetting(setting);
        fragment.setFlag("1");
        fragment.setshowGestureLock(true);
        fragment.setIsVerifiPwd(true);
        fragment.show(getFragmentManager(), "PasswordLockFragment");
        dismissDialog();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        fragmentq = null;
    }
}
