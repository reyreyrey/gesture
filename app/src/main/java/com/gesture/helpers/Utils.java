package com.gesture.helpers;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.gesture.R;

import java.security.MessageDigest;

/**
 * Created by Rea.X on 2017/1/25.
 */

public class Utils {


    public static Drawable getApplicationIcon(Context context){
        try{
            ApplicationInfo info = context.getApplicationInfo();
            PackageManager pm = context.getPackageManager();
            return info.loadIcon(pm);
        }catch (Exception e){
            return context.getResources().getDrawable(R.mipmap.ic_launcher);
        }
    }
    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte [] b = md.digest();
            int i;
            StringBuffer sb = new StringBuffer();
            for(int offset=0;offset<b.length;offset++){
                i = b[offset];
                if(i<0)i+=256;
                if(i<16)sb.append("0");
                sb.append(Integer.toHexString(i));
            }
            return sb.toString();
        } catch (Exception e) {
        }
        return "";
    }
}
