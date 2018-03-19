
package com.gesture.helpers;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gesture.R;


public class MyToast extends Toast {
    Context context;

    public TextView title;

    public TextView content;

    private View layout;

    public MyToast(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        layout = inflater.inflate(R.layout.customtoast, null);
        title = (TextView) layout.findViewById(R.id.title);
        content = (TextView) layout.findViewById(R.id.content);
        setView(layout);
        setGravity(Gravity.CENTER, 0, 0);
    }

    public static MyToast makeText(Context context, String content, int length) {
        MyToast toast = new MyToast(context);
        toast.title.setText("温馨提示");
        toast.content.setText(content);
        toast.setDuration(length);
        return toast;
    }
}
