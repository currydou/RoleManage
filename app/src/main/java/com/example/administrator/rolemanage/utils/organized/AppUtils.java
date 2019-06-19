package com.example.administrator.rolemanage.utils.organized;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;

import com.example.administrator.rolemanage.base.App;
import com.example.administrator.rolemanage.utils.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * <p/>
 * App工具类
 */
@SuppressWarnings({"unused", "JavaDoc"})
public final class AppUtils {

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    /**
     * 初始化工具类
     *
     * @param app 应用
     */
    public static void init(@NonNull final Application app) {
        AppUtils.sApplication = app;
    }

    public static RxErrorHandler getRxHandler() {
        if (sApplication instanceof App)
            return ((App) sApplication).getRxErrorHandler();
        else
            throw new IllegalStateException("application not a com.yodoo.atinvoice.base.APP");
    }

    public static ImageLoader getImageLoader() {
        if (sApplication instanceof App)
            return ((App) sApplication).getImageLoader();
        else
            throw new IllegalStateException("application not a com.yodoo.atinvoice.base.APP");
    }

    /*------------------------------------------------------------------------------------------*/


    public static boolean isNetAvailable(Context context) {
        ConnectivityManager cwjManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        return !(cwjManager == null || (networkInfo = cwjManager.getActiveNetworkInfo()) == null) && networkInfo.isAvailable();
    }


    /**
     * 实现文本复制功能
     *
     * @param content
     */
    public static void copy(Context context, String content) {
        if (Build.VERSION.SDK_INT >= 11) {
            // 得到剪贴板管理器
            android.content.ClipboardManager cmb = (ClipboardManager) context
                    .getSystemService(CLIPBOARD_SERVICE);
            if (cmb != null) {
                cmb.setText(content.trim());
            }
        } else {
            android.text.ClipboardManager c = (android.text.ClipboardManager)
                    context.getSystemService(CLIPBOARD_SERVICE);
            if (c != null) {
                c.setText(content.trim());
            }
        }
    }


}
