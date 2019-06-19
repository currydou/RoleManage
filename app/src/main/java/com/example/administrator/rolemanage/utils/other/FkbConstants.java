package com.example.administrator.rolemanage.utils.other;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *	常量封装
 */
@SuppressLint("SimpleDateFormat")
public final class FkbConstants {

	public static final boolean DEBUG = true;

	public static final class IzhuoCaches {
		public static final Map<String, Activity> ACTIVITY_MAP = new LinkedHashMap<String, Activity>();
		// 存放含有索引字母的位置
		public static final Map<String, Integer> SELECTORS = new HashMap<String, Integer>();
	}

	public static final class IzhuoKey {
		public static final String DATA = "data";
		public static final String COMMAND = "command";

		public static final String PAGE = "page";
		public static final String PAGE_SIZE = "pageSize";

		public static final String RESULT = "result";
		public static final String MSG = "msg";
		public static final String CODE = "code";
		public static final String CONTENT = "content";

		public static final String DATE = "date";
		public static final String LONGITUDE = "longitude";
		public static final String LATITUDE = "latitude";
		public static final String ADDRESS = "address";
		public static final String PICTURE = "picture";
		public static final String CITY = "city";

		public static final String HTTP_HEAD = "http://";
		public static final String HTTPS_HEAD = "https://";
		public static final String ERROR = "error";

		public static final String URL = "url";
	}

	public static final class IzhuoError {

		public static final String NO_RESPONSE = "no response";
		public static final String JSON_ERROR = "json error";
		
		public static final int CODE_SERVER_ERROR = 0x000500;

		public static final SparseArray<String> ERROR_MAP = new SparseArray<String>();

		static {
			ERROR_MAP.put(CODE_SERVER_ERROR, "未知异常！");
		}
		
		public static final void put(int key, String value) {
			ERROR_MAP.put(key, value);
		}
		
		public static final String get(int key) {
			return ERROR_MAP.get(key);
		}
	}

}
