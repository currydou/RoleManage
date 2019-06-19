package com.example.administrator.rolemanage.utils.organized;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.annotations.NonNull;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class PermissionUtil {
    public static final String TAG = "Permission";


    private PermissionUtil() {
    }

    public interface RequestPermission {
        /**
         * 权限请求成功
         */
        void onRequestPermissionSuccess();

        /**
         * 用户拒绝了权限请求, 权限请求失败, 但还可以继续请求该权限
         *
         * @param permissions 请求失败的权限名
         */
        void onRequestPermissionFailure(List<String> permissions);

        /**
         * 用户拒绝了权限请求并且用户选择了以后不再询问, 权限请求失败, 这时将不能继续请求该权限, 需要提示用户进入设置页面打开该权限
         *
         * @param permissions 请求失败的权限名
         */
        void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions);
    }


    public static void requestPermission(final RequestPermission requestPermission, RxPermissions rxPermissions, RxErrorHandler errorHandler, String... permissions) {
        if (permissions == null || permissions.length == 0) return;

        List<String> needRequest = new ArrayList<>();
        for (String permission : permissions) { //过滤调已经申请过的权限
            if (!rxPermissions.isGranted(permission)) {
                needRequest.add(permission);
            }
        }

        if (needRequest.size() == 0) {//全部权限都已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess();
        } else {//没有申请过,则开始申请
            rxPermissions
                    .requestEach(needRequest.toArray(new String[needRequest.size()]))
                    .buffer(permissions.length)
                    .subscribe(new ErrorHandleSubscriber<List<Permission>>(errorHandler) {
                        @Override
                        public void onNext(@NonNull List<Permission> permissions) {
                            for (Permission p : permissions) {
                                if (!p.granted) {
                                    if (p.shouldShowRequestPermissionRationale) {
                                        requestPermission.onRequestPermissionFailure(Arrays.asList(p.name));
                                        return;
                                    } else {
                                        requestPermission.onRequestPermissionFailureWithAskNeverAgain(Arrays.asList(p.name));
                                        return;
                                    }
                                }
                            }
                            requestPermission.onRequestPermissionSuccess();
                        }
                    });
        }

    }


    /**
     * 请求录音
     */
    public static void recordAudio(RequestPermission requestPermission, RxPermissions rxPermissions, RxErrorHandler errorHandler) {
        requestPermission(requestPermission, rxPermissions, errorHandler, Manifest.permission.RECORD_AUDIO, Manifest.permission.RECORD_AUDIO);
    }

    /**
     * 请求摄像头权限
     */
    public static void launchCamera(RequestPermission requestPermission, RxPermissions rxPermissions, RxErrorHandler errorHandler) {
        requestPermission(requestPermission, rxPermissions, errorHandler, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }


    /**
     * 请求外部存储的权限
     */
    public static void externalStorage(RequestPermission requestPermission, RxPermissions rxPermissions, RxErrorHandler errorHandler) {
        requestPermission(requestPermission, rxPermissions, errorHandler, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }


    /**
     * 请求发送短信权限
     */
    public static void sendSms(RequestPermission requestPermission, RxPermissions rxPermissions, RxErrorHandler errorHandler) {
        requestPermission(requestPermission, rxPermissions, errorHandler, Manifest.permission.SEND_SMS);
    }


    /**
     * 请求打电话权限
     */
    public static void callPhone(RequestPermission requestPermission, RxPermissions rxPermissions, RxErrorHandler errorHandler) {
        requestPermission(requestPermission, rxPermissions, errorHandler, Manifest.permission.CALL_PHONE);
    }


    /**
     * 请求获取手机状态的权限
     */
    public static void readPhoneState(RequestPermission requestPermission, RxPermissions rxPermissions, RxErrorHandler errorHandler) {
        requestPermission(requestPermission, rxPermissions, errorHandler, Manifest.permission.READ_PHONE_STATE);
    }


    /**
     * 请求联系人的权限
     */
    public static void writeContact(RequestPermission requestPermission, RxPermissions rxPermissions, RxErrorHandler errorHandler) {
        requestPermission(requestPermission, rxPermissions, errorHandler, Manifest.permission.WRITE_CONTACTS);
    }

    /**
     * 请求联系人的权限
     */
    public static void accessFineLocation(RequestPermission requestPermission, RxPermissions rxPermissions, RxErrorHandler errorHandler) {
        requestPermission(requestPermission, rxPermissions, errorHandler, Manifest.permission.ACCESS_FINE_LOCATION);
    }



    public static boolean obtainPermission(Activity activity, String permission, int requestCode, Runnable runnable) {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(activity,
                permission) != PackageManager.PERMISSION_GRANTED) {
            // 申请WRITE_EXTERNAL_STORAGE权限
            activity.requestPermissions(new String[]{permission}, requestCode);
            return false;
        } else {
            runnable.run();
            return true;
        }
    }

    public static void onRequestPermissionsResult(int[] grantResults, Runnable runnable) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 获取权限成功
            runnable.run();
        } else {
            // 获取权限失败
        }
    }
}

