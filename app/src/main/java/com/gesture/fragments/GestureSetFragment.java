package com.gesture.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gesture.R;
import com.gesture.helpers.GestureHelper;
import com.gesture.helpers.MySuccessToast;
import com.gesture.helpers.MyToast;
import com.gesture.views.Lock9View;
import com.gesture.views.ShowGestureView;
import com.rea.commonUtils.log.LogUtil;

/**
 * Created by Rea.X on 2017/1/25.
 */

public class GestureSetFragment extends GestureBaseFragment implements View.OnClickListener{

    //toolbar
    private Toolbar toolbar;
    //标题栏
    private TextView tv_title;
    //跳过
    private Button btn_right;
    private TextView tv_skip;
    //说明文字
    private TextView tv_gesture_text;
    //
    //第一次绘制的密码
    private String fristPassword;
    //显示上次选择的密码
    private ShowGestureView showgesview;


    private static GestureSetFragment fragmentq;

    public static GestureSetFragment getInstant() {
        if (fragmentq == null)
            fragmentq = new GestureSetFragment();
        return fragmentq;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentView() {
        return R.layout.layout_gesture_set;
    }

    @Override
    protected void init() {
        setCancelable(false);
        initView();
        initData();
    }

    private void initData() {
        toolbar.setTitle("");
        if (toolbarBackground != 0) {
            toolbar.setBackgroundResource(toolbarBackground);
        } else {
            toolbar.setBackgroundColor(toolbarColor);
        }

        tv_title.setTextColor(toolbarTextcolor);
        btn_right.setOnClickListener(this);
        lock_9_view.setCallBack(new GesLis());
        tv_skip.setOnClickListener(this);
        if (setting != null) {
            tv_skip.setTextColor(setting.getLinkTextColor());
            tv_gesture_text.setTextColor(setting.getNomalTextColor());
        }
    }

    private void initView() {
        toolbar = $(R.id.toolbar);
        tv_skip = $(R.id.tv_skip);
        tv_title = $(R.id.tv_title);
        btn_right = $(R.id.btn_right);
        tv_gesture_text = $(R.id.tv_gesture_text);
        showgesview = $(R.id.showgesview);
        if (setting != null) {
            showgesview.setImage(setting);
            tv_skip.setVisibility(setting.showSkipGesture() ? View.VISIBLE : View.INVISIBLE);
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_right || view.getId() == R.id.tv_skip) {
            GestureHelper.setHasOpenGesture(getActivity(), false);
            listener.skipGesture(this);
        }
    }


    private class GesLis implements Lock9View.CallBack{

        @Override
        public void onFinish(String s) {
            if (TextUtils.isEmpty(fristPassword)) {
                if (s.length() < GestureHelper.MIN_LINE_NUM) {
                    MyToast.makeText(getActivity(), String.format(getString(R.string.ges_min_line), GestureHelper.MIN_LINE_NUM), Toast.LENGTH_SHORT).show();
                    return;
                }
                fristPassword = s;
                tv_gesture_text.setText(getString(R.string.draw_agein));
                showgesview.setPassword(s);
                return;
            }
            if (fristPassword.equals(s)) {
                MySuccessToast.makeText(getActivity(), getString(R.string.ges_set_success),
                        Toast.LENGTH_LONG).show();
                fristPassword = null;
                GestureHelper.reset(getActivity());
                GestureHelper.setHasOpenGesture(getActivity(), true);
                GestureHelper.setGesturePassword(getActivity(), s);
                GestureHelper.setIsFrist(getActivity(), false);
                listener.setGestureFinish(GestureSetFragment.this);
                return;
            }
            MyToast.makeText(getActivity(), getString(R.string.ges_no_same), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        LogUtil.e("onDismiss>>>>>>>>>>>>>>");
        fragmentq = null;
    }
}
