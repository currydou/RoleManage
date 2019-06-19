package com.example.administrator.rolemanage.utils.organized;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * 全屏是不显示状态栏；
 * 设置LayoutParams.FLAG_TRANSLUCENT_STATUS，即状态栏透明，沉浸式模式
 */

public class ImmersedStatusBarUtils {
    /**
     * 在{@link Activity#setContentView}之后调用
     *
     * @param activity       要实现的沉浸式状态栏的Activity
     * @param titleViewGroup 头部控件的ViewGroup,若为null,整个界面将和状态栏重叠
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void initAfterSetContentView(Activity activity,
                                               View titleViewGroup) {
        if (activity == null)
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (titleViewGroup == null)
            return;
        // 设置头部控件ViewGroup的PaddingTop,防止界面与状态栏重叠
        int statusBarHeight = getStatusBarHeight(activity);
        titleViewGroup.setPadding(0, statusBarHeight, 0, 0);

    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void setStatusBar(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity,color));
        }
    }

    /**
     * 获取状态栏的高度
     */
    public static int getStatusBarHeight(Activity context) {
        int statusBarHeight;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
            Rect frame = new Rect();
            context.getWindow().getDecorView()
                    .getWindowVisibleDisplayFrame(frame);
            statusBarHeight = frame.top;
        }
        return statusBarHeight;
    }

    public static boolean isCanSetStatusBarColor() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}