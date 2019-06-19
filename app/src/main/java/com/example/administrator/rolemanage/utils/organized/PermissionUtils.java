package com.example.administrator.rolemanage.utils.organized;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;


/**
 * 直接调用 obtainPermission 方法，在回调中 调用hasSelfPermission
 *
 * 拍照、相册的两个权限：Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA
 *
 *
 */

public class PermissionUtils {

    /*-------------------------------第一种-------------需要在permission回调里处理-----------------------------------*/

    public static boolean obtainPermission(@NonNull Activity context, int requestCode, @NonNull String... permissions) {
        List<String> notPermissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= 23 && !checkSelfPermissions(context.getApplication(), notPermissions, permissions)) {
            ActivityCompat.requestPermissions(context, notPermissions.toArray(new String[notPermissions.size()]), requestCode);
            return false;
        } else {
            return true;
        }
    }

    private static boolean checkSelfPermissions(@NonNull Context context, List<String> notPermissions, @NonNull String... permissions) {
        if (notPermissions == null) {
            notPermissions = new ArrayList<>();
        }
        // boolean hasPermission = true;
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                // hasPermission = false;
                // break;
                notPermissions.add(permission);
            }
        }
        return notPermissions.size() == 0;
    }

    /**
     * 返回false提示权限获取失败
     *
     * @param grantResults
     * @return
     */
    public static boolean hasSelfPermission(int requestCode, @NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /*------------------------第二种----------同意后还需要点击一次，交互不太好--------------------------------------*/

    public static boolean checkSelfPermission(@NonNull Activity context, int requestCode, @NonNull String... permissions) {
        List<String> notPermissions = new ArrayList<>();
        if (!checkSelfPermissions(context.getApplication(), notPermissions, permissions)) {
            ActivityCompat.requestPermissions(context, notPermissions
                    .toArray(new String[notPermissions.size()]), requestCode);
            return false;
        }
        return true;
    }

    public static boolean checkSelfPermissions(@NonNull Context context, @NonNull String... permissions) {
        return checkSelfPermissions(context, null, permissions);
    }

    public static boolean checkPermissions(@NonNull Context context, List<String> notPermissions, @NonNull String... permissions) {
        if (notPermissions == null) {
            notPermissions = new ArrayList<>();
        }
        // boolean hasPermission = true;
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                // hasPermission = false;
                // break;
                notPermissions.add(permission);
            }
        }
        return notPermissions.size() == 0;
    }

    public static boolean hasSelfPermission(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


}