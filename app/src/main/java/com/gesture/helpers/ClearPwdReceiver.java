package com.gesture.helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.rea.commonUtils.log.LogUtil;

/**
 * author: Rea.X
 * date: 2017/4/7.
 */

public class ClearPwdReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.e("setShouldClearGesturePasswordNum------");
        GestureHelper.reset(context);
    }
}
