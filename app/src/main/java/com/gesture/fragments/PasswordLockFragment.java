package com.gesture.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gesture.R;
import com.gesture.helpers.GestureHelper;
import com.gesture.helpers.Utils;

/**
 * Created by Rea.X on 2017/2/24.
 */

public class PasswordLockFragment extends GestureBaseFragment implements View.OnClickListener {
    private ImageView imageview_logo, img_hide;
    private EditText et_password;
    private Button btn_ok;
    private TextView tv_gesture_lock, tv_forgetpassword, tv_text;
//    private String oldPassword;

    private LinearLayout layout_user;
    private EditText et_user;

    //是否是验证手势密码
    private boolean isVerifiPwd;

    //是否显示手势解锁按钮
    private boolean showGestureLock;

    @Override
    protected int getContentView() {
        return R.layout.layout_gesture_password_lock;
    }



    private static  PasswordLockFragment fragmentq;
    public static PasswordLockFragment getInstant(){
        if(fragmentq == null){
            fragmentq = new PasswordLockFragment();
        }
        return fragmentq;
    }

    public void setIsVerifiPwd(boolean isVerifiPwd){
        this.isVerifiPwd = isVerifiPwd;
    }

    @Override
    protected void init() {
        setCancelable(false);
        initView();
//        oldPassword = GestureHelper.getLoginPwd(getActivity());
        if (setting != null) {
            imageview_logo.setImageDrawable(setting.getIcon() == 0 ? Utils.getApplicationIcon(getActivity()) : getResources().getDrawable(setting.getIcon()));
            tv_gesture_lock.setTextColor(setting.getLinkTextColor());
            tv_forgetpassword.setTextColor(setting.getLinkTextColor());
        }
    }

    private void initView() {
        et_user = $(R.id.et_user);
        layout_user = $(R.id.layout_user);
        tv_text = $(R.id.tv_text);
        img_hide = $(R.id.img_hide);
        imageview_logo = $(R.id.imageview_logo);
        et_password = $(R.id.et_password);
        btn_ok = $(R.id.btn_ok);
        tv_gesture_lock = $(R.id.tv_gesture_lock);
        tv_gesture_lock.setVisibility(showGestureLock ? View.VISIBLE : View.INVISIBLE);
        tv_forgetpassword = $(R.id.tv_forgetpassword);
        btn_ok.setOnClickListener(this);
        tv_gesture_lock.setOnClickListener(showGestureLock ? this : null);
        tv_forgetpassword.setOnClickListener(this);
        img_hide.setOnClickListener(this);
        if (setting != null && setting.showUser()) {
            layout_user.setVisibility(View.VISIBLE);
            et_user.setText(setting.getLoginName() == null ? "" : setting.getLoginName());
        }else{
            layout_user.setVisibility(View.GONE);
        }

        boolean eyeIconIsClosedDefault = setting == null ? false : setting.eyeIconIsClosedDefault();
        if(!eyeIconIsClosedDefault){
            img_hide.setImageResource(R.mipmap.password_off);
            img_hide.setTag("hide");
        }else{
            img_hide.setImageResource(R.mipmap.password_on);
            img_hide.setTag("show");
        }
    }

    private String flag;
    public void setFlag(String flag){
        this.flag = flag;
    }


    public void setshowGestureLock(boolean showGestureLock){
        this.showGestureLock = showGestureLock;
    }
    @Override
    public void onClick(View view) {
        if (setting == null) {
            return;
        }
        if (view.getId() == R.id.btn_ok) {
            String pwd = et_password.getText().toString().trim();
//            pwd = Utils.md5(pwd + GestureConst.PASSWORD_LAST);
            String password = setting.getPassword();
            boolean pwdIsRight = false;
            if(TextUtils.isEmpty(password)){
                pwdIsRight = setting.onCheckPwd(pwd);
            }else{
                pwd = Utils.md5(pwd);
                pwdIsRight = setting.getPassword().equals(pwd);
            }
            if(pwdIsRight){
//                if(isVerifiPwd){
//                    Helper.openGestureSetting((AppCompatActivity) getActivity(), setting, listener);
//                    return;
//                }
                GestureHelper.setPwdLimitNumber(getActivity(), GestureHelper.MAX_PWD_NUMBER);
                GestureHelper.setLimitNumber(getActivity(), GestureHelper.MAX_TRY_NUMBER);
                listener.onVerFinish(this, flag, true);
            }else{
                listener.onVerFinish(this, flag, false);
                int num = GestureHelper.getPwdLimitNumber(getActivity());
                num--;
                if (num <= 0) {
                    dismiss();
                    listener.onClearUserData();
                    if(listener.onPasswordErrorMaxCount()){

                    }else{
                        getActivity().startActivity(new Intent(getActivity(), setting.getLoginActivity()));
                    }
                    return;
                }
                GestureHelper.setPwdLimitNumber(getActivity(), num);
                tv_text.setTextColor(Color.parseColor("#A00720"));
                tv_text.setText(String.format(getString(R.string.ges_error), num));
                tv_text.startAnimation(sharkAnim);
            }

        } else if (view.getId() == R.id.tv_gesture_lock) {
            GestureBaseFragment fragment = null;
            if(!isVerifiPwd){
                fragment = GestureLockFragment.getInstant();
            }else{
                fragment = GestureVerifiFragment.getInstant();
            }
            fragment.setGestureSetting(setting);
            fragment.setListener(listener);
            fragment.show(getFragmentManager(), "GestureLockFragment");
            dismissDialog();
        } else if (view.getId() == R.id.tv_forgetpassword) {
//            WebViewUtils.customer(getActivity());
            listener.onForgetPwd(this);
        } else if (view.getId() == R.id.img_hide) {
            if (img_hide.getTag().equals("hide")) {
                img_hide.setImageResource(R.mipmap.password_on);
                if(setting.eyeIconIsClosedDefault()){
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else{
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

                img_hide.setTag("show");
            } else {
                img_hide.setImageResource(R.mipmap.password_off);
                if(setting.eyeIconIsClosedDefault()){
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                img_hide.setTag("hide");
            }
            return;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        fragmentq = null;
    }
}
