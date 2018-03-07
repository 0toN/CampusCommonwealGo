package cn.edu.tsu.campuscommonwealgo.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;


/**
 * Created by XWM on 2018/1/10.
 */

public class TransparentStatusBarUtil {
    /**
     * 去除Android 7.0及以上系统因设置状态栏透明后，系统自动给状态栏设置的一层透明黑色背景
     *
     * @param activity
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void setTransparent(Activity activity) {
        try {
            Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
            Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
            field.setAccessible(true);
            //改为透明
            field.setInt(activity.getWindow().getDecorView(), Color.TRANSPARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改状态栏的内容的颜色深色，适用于状态栏背景色为白色时导致状态栏的内容无法看清的情况
     *
     * @param activity
     */
    public static void setDarkColorContent(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    /**
     * 设置Android 5.0系统状态栏的背景色
     *
     * @param activity
     * @param corlor
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarColor(Activity activity, int corlor) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(corlor);
    }
}
