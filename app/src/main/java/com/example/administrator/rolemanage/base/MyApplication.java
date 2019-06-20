package com.example.administrator.rolemanage.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.example.administrator.rolemanage.R;
import com.example.administrator.rolemanage.utils.imageloader.ImageLoader;
import com.example.administrator.rolemanage.utils.organized.AppUtils;
import com.example.administrator.rolemanage.utils.organized.PerfUtil;
import com.example.administrator.rolemanage.utils.organized.ToastUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;


/**
 * Created by lib on 2017/2/24.
 */

public class MyApplication extends MultiDexApplication implements App{//MultiDexApplication

    public static Application instance;
    // 保存所有的Activity
    private List<Activity> activityList;
    public static ExecutorService limitedTaskExecutor;//  2018/4/8  用 ThreadPoolExecutor 自定义

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        instance = this;
        PerfUtil.init(this);
        AppUtils.init(this);
        limitedTaskExecutor = Executors.newFixedThreadPool(3);
//        PerfUtil.setString(Constant.SP_BASEURL, Constant.URL_BASE_API);
        ToastUtils.h = new Handler();
        //阿里云
        //BugLy
        initBugLy();
        //
    }



    private void initBugLy() {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), "2ba491c385", false);
    }

    /**
     * 添加activity到activityList集合中
     *
     * @param activity 每一個activity
     */

    public void addActivity(Activity activity) {
        if (activityList == null) {
            activityList = new ArrayList<>();
        }
        activityList.add(activity);
    }

    public int getListSize() {
        if (activityList != null) {
            return activityList.size();
        }
        return 0;
    }

    public void removeActivity(Activity activity) {
        if (activityList != null) {
            if (activityList.contains(activity)) {
                activityList.remove(activity);
            }
        }

    }

    /**
     * 清空列表，取消引用
     */
    public void clearActivity() {
        activityList.clear();
    }

    /**
     * app退出
     */
    public void exit() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing() && activity != null) {
                activity.finish();
            }
        }
        clearActivity();
        System.exit(0);
    }

    /**
     * 结束指定类名的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {

        if (activity != null) {
            activityList.remove(activity);
            activity.finish();
            activity = null;
        }

    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        for (int i = 0; i < activityList.size(); i++) {
            if (activityList.get(i).getClass().equals(cls)) {
                finishActivity(activityList.get(i));
            }
        }

    }

    private RxErrorHandler rxErrorHandler = RxErrorHandler
            .builder()
            .with(this)
            .responseErrorListener(new ResponseErrorListener() {
                @Override
                public void handleResponseError(Context context, Throwable t) {
                    ToastUtils.showToast(instance,instance.getString(R.string.network_abnormal));
                }
            }).build();

    @Override
    public RxErrorHandler getRxErrorHandler() {
        return rxErrorHandler;
    }

    @Override
    public ImageLoader getImageLoader() {
        return null;
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
