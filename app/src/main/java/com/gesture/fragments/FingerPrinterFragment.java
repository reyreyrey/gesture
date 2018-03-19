package com.gesture.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gesture.R;
import com.gesture.fingerPrinter.FingerHelper;
import com.gesture.listeners.GestureListener;

public class FingerPrinterFragment extends GestureBaseFragment implements GestureListener,View.OnClickListener {
    private TextView tv_use_gesture;
    private TextView tv_massage;
    private ImageView img_fingerprinter;

    private int mFpColorError;
    private int mFpColorNormal;
    private int mFpColorSuccess;

    private String flag;

    private static  FingerPrinterFragment fragment;
    public static FingerPrinterFragment getInstant(){
        if(fragment == null){
            fragment = new FingerPrinterFragment();
        }
        return fragment;
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_finger_printer;
    }

    @Override
    protected void init() {
        setCancelable(false);
        tv_use_gesture = $(R.id.tv_use_gesture);
        tv_massage = $(R.id.tv_massage);
        img_fingerprinter = $(R.id.img_fingerprinter);
        FingerHelper.start(getContext(), this, this, flag);
        if (setting != null) {
            tv_use_gesture.setTextColor(setting.getLinkTextColor());
            tv_massage.setTextColor(setting.getNomalTextColor());
        }
        mFpColorError = ContextCompat.getColor(getContext(), android.R.color.holo_red_dark);
        mFpColorNormal = ContextCompat.getColor(getContext(), R.color.finger_nomal);
        mFpColorSuccess = ContextCompat.getColor(getContext(), android.R.color.holo_green_dark);
        DrawableCompat.setTint(img_fingerprinter.getDrawable(), mFpColorNormal);

        tv_use_gesture.setOnClickListener(this);

    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

//    @Override
//    public void onPwdErrorMax(DialogFragment dialog) {
//        listener.onPwdErrorMax(dialog);
//    }

    @Override
    public void onClearUserData() {
        listener.onClearUserData();
    }

    @Override
    public boolean onPasswordErrorMaxCount() {
        return listener.onPasswordErrorMaxCount();
    }

    @Override
    public void onVerFinish(DialogFragment dialog, String flag, boolean isSuccess) {
        listener.onVerFinish(dialog, flag, isSuccess);
        if (!isSuccess) {
            DrawableCompat.setTint(img_fingerprinter.getDrawable(), mFpColorError);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    DrawableCompat.setTint(img_fingerprinter.getDrawable(), mFpColorNormal);
                }
            }, 2000);
        }
    }

    @Override
    public void skipGesture(DialogFragment dialog) {
        listener.skipGesture(dialog);
    }

    @Override
    public void setGestureFinish(DialogFragment dialog) {
        listener.setGestureFinish(dialog);
    }

    @Override
    public void onForgetPwd(DialogFragment dialog) {
        listener.onForgetPwd(dialog);
    }

    @Override
    public void toggleGestureStatus(boolean isShow) {
        listener.toggleGestureStatus(isShow);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.tv_use_gesture){
            GestureLockFragment fragment = GestureLockFragment.getInstant();
            fragment.setFlag(flag);
            fragment.setListener(listener);
            fragment.setGestureSetting(setting);
            fragment.show(getFragmentManager(), "GestureLockFragment");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            }, 1000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FingerHelper.stop();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        fragment = null;
    }
}
