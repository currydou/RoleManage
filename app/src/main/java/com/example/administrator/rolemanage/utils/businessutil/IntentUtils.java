package com.example.administrator.rolemanage.utils.businessutil;


import android.content.Context;
import android.content.Intent;

import com.example.administrator.rolemanage.module.MainActivity;
import com.example.administrator.rolemanage.module.login.LoginActivity;
import com.example.administrator.rolemanage.module.login.OffLineActivity;
import com.example.administrator.rolemanage.utils.organized.PerfUtil;

/**
 * Created by Libin on 2016/8/1.
 * activity跳转
 */
public class IntentUtils {


    public static final String RESULT_CHECKEDLIST = "RESULT_CHECKEDLIST";
    public static final String RESULT_CHECKEDITEM = "RESULT_CHECKEDITEM";
    public static final String RESULT_ADD_ITEM = "RESULT_ADD_ITEM";

    public static final String INTENT_KEY_TEL = "intent_key_tel";
    public static final String INTENT_KEY_VALIDATE_CODE = "intent_key_validate_code";
    public static final String INTENT_KEY_WX_INFO = "intent_key_wx_info";


    public static void entryMain(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }



    public static void entryLogin(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void entryLogin2(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static void entryTokenNotValide(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(context, OffLineActivity.class);
        context.startActivity(intent);
    }

    public static void logOut(Context context) {
        clearUserData();
        entryLogin2(context);
    }

    public static void clearUserData() {
        PerfUtil.setUserInfo(null);
    }


}
