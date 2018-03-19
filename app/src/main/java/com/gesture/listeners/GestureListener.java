package com.gesture.listeners;

import android.support.v4.app.DialogFragment;


/**
 * Created by Rea.X on 2017/1/24.
 * <p>手势监听类</p>
 */

public interface GestureListener {
    void onClearUserData();

    /**
     * 密码输入错误次数达到最高
     * @return true:表示自己处理
     */
    boolean onPasswordErrorMaxCount();


    /**
     * 手势验证结束
     *
     * @param isSuccess
     */
    void onVerFinish(DialogFragment dialog, String flag, boolean isSuccess);


    /**
     * 跳过设置解锁密码，需要跳转
     */
    void skipGesture(DialogFragment dialog);

    /**
     * 设置手势密码结束
     */
    void setGestureFinish(DialogFragment dialog);

    /**
     * 点击忘记密码
     *
     * @param dialog
     */
    void onForgetPwd(DialogFragment dialog);


    /**
     * 切换开启关闭状态
     * @param isShow
     */
    void toggleGestureStatus(boolean isShow);

}
