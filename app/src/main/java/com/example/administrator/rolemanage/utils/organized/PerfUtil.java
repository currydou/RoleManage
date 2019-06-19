package com.example.administrator.rolemanage.utils.organized;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.administrator.rolemanage.constant.PerfUtilConstant;
import com.example.administrator.rolemanage.model.UserInfo;
import com.google.gson.Gson;

/**
 * Created by lib on 2017/2/24.
 */

public class PerfUtil {
    private static final String P_NAME = "preferences";
    private static SharedPreferences preferences;
    private static UserInfo userInfo;
    private static UserInfo ticketCache;

    public static void init(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(P_NAME, 0);
        }
    }

    public static String getString(String key) {
        return preferences.getString(key, "");
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public static int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public static int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public static void setString(String key, String value) {
        SharedPreferences.Editor e = preferences.edit().putString(key, value);
        e.commit();
    }

    public static void setBoolean(String key, boolean value) {
        SharedPreferences.Editor e = preferences.edit().putBoolean(key, value);
        e.commit();
    }

    public static void setInt(String key, int value) {
        SharedPreferences.Editor e = preferences.edit().putInt(key, value);
        e.commit();
    }

    public static boolean isFirstEntry() {
        return getBoolean(PerfUtilConstant.SP_IS_FIRST_ENTRY, true);
    }

    public static void setFristEntry() {
        setBoolean(PerfUtilConstant.SP_IS_FIRST_ENTRY, false);
    }

    public static boolean isLogin() {
        return getUserInfo() != null;
    }

    public static void setUserInfo(UserInfo info) {
        if (info == null) {
            setString(PerfUtilConstant.SP_USER_INFO, "");
        } else {
            Gson gson = new Gson();
            String json = gson.toJson(info);
            setString(PerfUtilConstant.SP_USER_INFO, json);
        }
        userInfo = info;
    }

    public static UserInfo getUserInfo() {
        if (userInfo == null) {
            String json = PerfUtil.getString(PerfUtilConstant.SP_USER_INFO);
            if (TextUtils.isEmpty(json)) return null;
            Gson gson = new Gson();
            userInfo = gson.fromJson(json, UserInfo.class);
        }
        return userInfo;
    }

    public static UserInfo getUserInfoNotNull() {
        UserInfo userInfo = getUserInfo();
        if (userInfo == null) {
            userInfo = new UserInfo();
        }
        return userInfo;
    }

//    public static TicketCache getTicketInfo() {
//        if (ticketCache == null) {
//            String json = PerfUtil.getString(PerfUtilConstant.KEY_WX_TICKET);
//            if (TextUtils.isEmpty(json)) return null;
//            Gson gson = new Gson();
//            userInfo = gson.fromJson(json, UserInfo.class);
//        }
//        return ticketCache;
//    }

}
