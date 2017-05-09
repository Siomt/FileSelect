package com.zhouchatian.fileselect.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Toast统一管理类
 * Created by Mr.Robot on 2017/4/26.
 * https://github.com/TheSadFrog
 */
public class ToastUtil { 
 
    public static boolean isShow = true;
    private static Toast mToast;
    private static Handler mHandler;

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param text
     * @param duration
     */
    public static void showToast(final Context context, final CharSequence text, final int duration) {
        if(context == null){
            return;
        }
        if(Looper.myLooper()!=Looper.getMainLooper()){
            if (mHandler == null) {
                mHandler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        String str = (String) msg.obj;
                        Toast.makeText(context.getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                        super.handleMessage(msg);
                    }
                };
            }
            Message msg = mHandler.obtainMessage();
            msg.obj = text;
            msg.sendToTarget();
        }else {
            if(mToast == null){
                mToast = Toast.makeText(context.getApplicationContext(), text, duration);
            }else {
                mToast.setText(text);
                mToast.setDuration(duration);
            }
            mToast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param resid
     * @param duration
     */
    public static void showToast(final Context context, final int resid, final int duration) {
        if (null == context) {
			return;
		}
        String text = context.getResources().getString(resid);
        if(!TextUtils.isEmpty(text)) {
            showToast(context, text, duration);
        }
    }


    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow)
            //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            showToast(context, message, Toast.LENGTH_SHORT);

    } 
 
    /** 
     * 短时间显示Toast 
     * 
     * @param context 
     * @param message 
     */ 
    public static void showShort(Context context, int message) {
        if (isShow)
            //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            showToast(context, message, Toast.LENGTH_SHORT);
    } 
 
    /** 
     * 长时间显示Toast 
     * 
     * @param context 
     * @param message 
     */ 
    public static void showLong(Context context, CharSequence message) {
        if (isShow)
            //Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            showToast(context, message, Toast.LENGTH_LONG);
    } 
 
    /** 
     * 长时间显示Toast 
     * 
     * @param context 
     * @param message 
     */ 
    public static void showLong(Context context, int message) {
        if (isShow)
            //Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            showToast(context, message, Toast.LENGTH_LONG);
    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}