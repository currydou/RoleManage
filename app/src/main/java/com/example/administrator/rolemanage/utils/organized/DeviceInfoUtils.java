package com.example.administrator.rolemanage.utils.organized;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 *
 */
public class DeviceInfoUtils {

	private static TelephonyManager tm;

	private static void initManager(Context mContext) {
		tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
	}

	/**
	 * 获取设备号，返回当前移动终端的唯一标识，如果是GSM网络，返回IMEI；如果是CDMA网络，返回MEID
	 * 
	 * @param mContext
	 * @return
	 */
	public static String getDeviceId(Context mContext) {
		initManager(mContext);
		if (TextUtils.isEmpty(tm.getDeviceId())) {
			return "";
		}
		return tm.getDeviceId();
	}
}
