
package com.gesture.helpers;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.gesture.R;


public class MySuccessToast extends Toast {
    Context context;
    private View layout;

    public MySuccessToast(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        layout = inflater.inflate(R.layout.sucesstoast, null);
        setView(layout);
        setGravity(Gravity.CENTER, 0, 0);
    }

    public static MySuccessToast makeText(Context context, String content, int length) {
        MySuccessToast toast = new MySuccessToast(context);
        toast.setDuration(length);
        return toast;
    }
}
