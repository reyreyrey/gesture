package com.gesture.fingerPrinter;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.widget.Toast;

import com.gesture.R;
import com.gesture.fragments.FingerPrinterFragment;
import com.gesture.listeners.GestureListener;
import com.rea.commonUtils.log.LogUtil;


/**
 * Created by Rea.X on 2017/2/10.
 */

public class FingerHelper {
    private static KeyguardManager mKeyManager;
    private static FingerprintManagerCompat fingerprintManager = null;
    private static IFingerCallback authCallback = null;
    private static CancellationSignal cancellationSignal = null;

    @TargetApi(Build.VERSION_CODES.M)
    public static void start(Context context, GestureListener listener, FingerPrinterFragment fingerPrinterFragment, String flag) {
        if (!shouldUseFingerPrinter(context)) return;
        try {
            authCallback = new IFingerCallback(context.getApplicationContext(), listener, fingerPrinterFragment, flag);
            CryptoObjectHelper cryptoObjectHelper = new CryptoObjectHelper();
            if (cancellationSignal == null) {
                cancellationSignal = new CancellationSignal();
            }
            fingerprintManager.authenticate(cryptoObjectHelper.buildCryptoObject(), 0,
                    cancellationSignal, authCallback, null);
        } catch (Exception e) {
        }
    }

    public static String getFingerPrintNotOpenMessage(Context context) {
        mKeyManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        fingerprintManager = FingerprintManagerCompat.from(context);
        if (Build.VERSION.SDK_INT < 23) {
            //Toast.makeText(context, "不能在6.0系统一下运行", Toast.LENGTH_SHORT).show();
            LogUtil.e("不能在6.0系统一下运行");
            return context.getString(R.string.finger_system_is_lower);
        }
        if (!fingerprintManager.isHardwareDetected()) {
            // 没有指纹识别硬件
            LogUtil.e("没有指纹识别硬件");
            //Toast.makeText(context, "没有指纹识别硬件", Toast.LENGTH_SHORT).show();
            return context.getString(R.string.finger_no_haw);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (!mKeyManager.isKeyguardSecure()) {
                //没有开启锁屏密码
                LogUtil.e("没有开启锁屏密码");
                // Toast.makeText(context, "没有开启锁屏密码", Toast.LENGTH_SHORT).show();
                return context.getString(R.string.finger_no_lock_screen);
            }
        }

        if (!fingerprintManager.hasEnrolledFingerprints()) {
            //设备上没有指纹
            LogUtil.e("设备上没有指纹");
            // Toast.makeText(context, "设备上没有指纹", Toast.LENGTH_SHORT).show();
            return context.getString(R.string.finger_no_finger);
        }
        return null;
    }


    public static boolean shouldUseFingerPrinter(Context context) {
        return getFingerPrintNotOpenMessage(context) == null;
    }

    public static void stop() {
        authCallback = null;
    }
}
