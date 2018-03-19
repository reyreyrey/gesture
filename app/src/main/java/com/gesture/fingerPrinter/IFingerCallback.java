package com.gesture.fingerPrinter;

import android.content.Context;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.widget.Toast;

import com.gesture.fragments.FingerPrinterFragment;
import com.gesture.listeners.GestureListener;


/**
 * Created by Rea.X on 2017/2/10.
 */

public class IFingerCallback extends FingerprintManagerCompat.AuthenticationCallback {
    private GestureListener listener;
    private Context context;
    private FingerPrinterFragment fingerPrinterFragment;
    private String flag;

    public IFingerCallback(Context context, GestureListener listener, FingerPrinterFragment fingerPrinterFragment, String flag) {
        super();
        this.listener = listener;
        this.context = context;
        this.flag = flag;
        this.fingerPrinterFragment = fingerPrinterFragment;
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        super.onAuthenticationError(errMsgId, errString);
        if(errMsgId == 7){
//            listener.onPwdErrorMax(fingerPrinterFragment);
        }
        Toast.makeText(context, errString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        super.onAuthenticationHelp(helpMsgId, helpString);
        Toast.makeText(context, helpString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        listener.onVerFinish(fingerPrinterFragment, flag, true);
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        listener.onVerFinish(fingerPrinterFragment, flag, false);
    }
}
