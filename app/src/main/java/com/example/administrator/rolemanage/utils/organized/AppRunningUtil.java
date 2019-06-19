package com.example.administrator.rolemanage.utils.organized;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class AppRunningUtil {


    public static boolean isAppaLive(Context context, String str) {
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(10);
        boolean isAppRunning = false;
        for (ActivityManager.RunningTaskInfo info : list) {
            Log.e("isRunningForeground====", info.topActivity.getPackageName() + "####" + info.baseActivity.getPackageName());
            if (info.topActivity.getPackageName().equals(str) || info.baseActivity.getPackageName().equals(str)) {
                isAppRunning = true;
                break;
            }
        }
        return isAppRunning;
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                Log.e("1111111111111", "isAppIsInBackground: " + processInfo.processName + "--" + processInfo.pkgList);
                //前台程序
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

   /* public static boolean isRunningForeground2(Context context) {
        String top;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) { //For versions less than lollipop
            ActivityManager am = ((ActivityManager) context.getSystemService(ACTIVITY_SERVICE));
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(5);
            top = taskInfo.get(0).topActivity.getPackageName();
            Log.v("foreground2", "top app = " + top);
        } else { //For versions Lollipop and above
            List<AndroidAppProcess> processes = ProcessManager.getRunningForegroundApps(getApplicationContext());
            Collections.sort(processes, new ProcessManager.ProcessComparator());
            for (int i = 0; i <= processes.size() - 1; i++) {
                if (processes.get(i).name.equalsIgnoreCase("com.google.android.gms")) { //always the package name above/below this package is the top app
                    if ((i + 1) <= processes.size() - 1) { //If processes.get(i+1) available, then that app is the top app
                        top = processes.get(i + 1).name;
                    } else if (i != 0) { //If the last package name is "com.google.android.gms" then the package name above this is the top app
                        top = processes.get(i - 1).name;
                    } else {
                        if (i == processes.size() - 1) { //If only one package name available
                            top = processes.get(i).name;
                        }
                    }
                    Log.v(TAG, "top app = " + top);
                }
            }
        }
    }*/

    /*------------------------------------------------------------------------------------------*/

    /**
     * 方法描述：判断某一应用是否正在运行
     * Created by cafeting on 2017/2/4.
     *
     * @param context     上下文
     * @param packageName 应用的包名
     * @return true 表示正在运行，false 表示没有运行
     */
    public static boolean isAppRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        if (list.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    //获取已安装应用的 uid，-1 表示未安装此应用或程序异常
    public static int getPackageUid(Context context, String packageName) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
            if (applicationInfo != null) {
//                Logger.d(applicationInfo.uid);
                return applicationInfo.uid;
            }
        } catch (Exception e) {
            return -1;
        }
        return -1;
    }

    /**
     * 判断某一 uid 的程序是否有正在运行的进程，即是否存活
     * Created by cafeting on 2017/2/4.
     *
     * @param context 上下文
     * @param uid     已安装应用的 uid
     * @return true 表示正在运行，false 表示没有运行
     */
    public static boolean isProcessRunning(Context context, int uid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = am.getRunningServices(200);
        if (runningServiceInfos.size() > 0) {
            for (ActivityManager.RunningServiceInfo appProcess : runningServiceInfos) {
                if (uid == appProcess.uid) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean testAppLive(Context context) {
        boolean flag = false;
        String pName = context.getPackageName();
        int uid = getPackageUid(context, pName);
        if (uid > 0) {
            boolean rstA = isAppRunning(context, pName);
            boolean rstB = isProcessRunning(context, uid);
            if (rstA || rstB) {
                //指定包名的程序正在运行中
                flag = true;
            } else {
                //指定包名的程序未在运行中
                flag = false;
            }
        } else {
            //应用未安装
        }
        return flag;
    }
}
