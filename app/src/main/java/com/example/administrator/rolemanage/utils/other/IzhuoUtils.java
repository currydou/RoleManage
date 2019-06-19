/**
 * Copyright  All right reserved by IZHUO.NET.
 */
package com.example.administrator.rolemanage.utils.other;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Changlei
 *
 *         2014年8月1日
 */
public class IzhuoUtils {

	public static final String TAG = "PushDemoActivity";
	public static final String RESPONSE_METHOD = "method";
	public static final String RESPONSE_CONTENT = "content";
	public static final String RESPONSE_ERRCODE = "errcode";
	protected static final String ACTION_LOGIN = "com.baidu.pushdemo.action.LOGIN";
	public static final String ACTION_MESSAGE = "com.baiud.pushdemo.action.MESSAGE";
	public static final String ACTION_RESPONSE = "bccsclient.action.RESPONSE";
	public static final String ACTION_SHOW_MESSAGE = "bccsclient.action.SHOW_MESSAGE";
	protected static final String EXTRA_ACCESS_TOKEN = "access_token";
	public static final String EXTRA_MESSAGE = "message";

	public static String logStringCache = "";

	public static final double EARTH_RADIUS = 6378.137;// 地球半径

	public static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##.#");

	public static String getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
		return getDistance(null, lat1, lng1, lat2, lng2);
	}

	/**
	 * 通过经纬度计算距离
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static String getDistance(Context context, double lat1, double lng1,
                                     double lat2, double lng2) {
		// double radLat1 = rad(lat1);
		// double radLat2 = rad(lat2);
		// double a = radLat1 - radLat2;
		// double b = rad(lng1) - rad(lng2);
		// double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
		// + Math.cos(radLat1) * Math.cos(radLat2)
		// * Math.pow(Math.sin(b / 2), 2)));
		// s = s * EARTH_RADIUS;
		// s = Math.round(s * 10000) / 10000;
		// if (s < 1000) {
		// return context.getString(R.string.lable_meter, s);
		// } else {
		// return (s / 1000) + "km";
		// }
		double s = getShortDistance(lng1, lat1,
				lng2, lat2);
		return formatDistance(context, s);
	}

	/*
	 * 用于计算兴趣点的距离
	 */
	static double DEF_PI = 3.14159265359; // PI
	static double DEF_2PI = 6.28318530712; // 2*PI
	static double DEF_PI180 = 0.01745329252; // PI/180.0
	static double DEF_R = 6370693.5; // radius of earth

	/*
	 * 计算兴趣点的距离
	 */
	public static double getShortDistance(double lon1, double lat1,
			double lon2, double lat2) {
		double ew1, ns1, ew2, ns2;
		double dx, dy, dew;
		double distance;
		// 角度转换为弧度
		ew1 = lon1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lon2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 经度差
		dew = ew1 - ew2;
		// 若跨东经和西经180 度，进行调整
		if (dew > DEF_PI)
			dew = DEF_2PI - dew;
		else if (dew < -DEF_PI)
			dew = DEF_2PI + dew;
		dx = DEF_R * Math.cos(ns1) * dew;
		// 东西方向长度(在纬度圈上的投影长度)
		dy = DEF_R * (ns1 - ns2);
		// 南北方向长度(在经度圈上的投影长度)
		// 勾股定理求斜边长
		distance = Math.sqrt(dx * dx + dy * dy);
		return distance;
	}

	public static String formatDistance(Context context, double s) {
		if (s < 1000) {
			return DECIMAL_FORMAT.format(s) + "m";
		} else {
			return DECIMAL_FORMAT.format((s / 1000)) + "km";
		}
	}

	public static String formatDistance(double s) {
		return formatDistance(null, s);
	}

	/**
	 * 计算控件的宽高
	 * 
	 * @param view
	 *            需要计算宽高的view
	 * @return int[] 第一个元素为宽第二个元素为高
	 */
	public static int[] getWidgetWidthAndHeight(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		int height = view.getMeasuredHeight();
		int width = view.getMeasuredWidth();
		return new int[] { width, height };
	}

	public static String ToSBC(String input) {
		// 半角转全角：
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 32) {
				c[i] = (char) 12288;
				continue;
			}
			if (c[i] < 127)
				c[i] = (char) (c[i] + 65248);
		}
		return new String(c);
	}

	public static String getBitmapEncode(Bitmap bitmap) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
		try {
			out.flush();
			out.close();
			byte[] buffer = out.toByteArray();
			byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
			return new String(encode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 全角转换为半角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 设置textview字体加粗
	 * 
	 * @param view
	 */
	public static void fontOverstriking(TextView view) {
		TextPaint tp = view.getPaint();
		tp.setFakeBoldText(true);
	}

	/**
	 * 获取一个时间点距离今天的天数
	 * 
	 * @param nowDate
	 * @param formerlyDate
	 * @return
	 */
	public static long getQuot(Date nowDate, Date formerlyDate) {
		long quot = nowDate.getTime() - formerlyDate.getTime();
		quot = quot / 1000 / 60 / 60 / 24;
		return quot;
	}

	// 获取ApiKey
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {

		}
		return apiKey;
	}

	// 用share preference来实现是否绑定的开关。在ionBind且成功时设置true，unBind且成功时设置false
	public static boolean hasBind(Context context) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		String flag = sp.getString("bind_flag", "");
		if ("ok".equalsIgnoreCase(flag)) {
			return true;
		}
		return false;
	}

	public static void setBind(Context context, boolean flag) {
		String flagStr = "not";
		if (flag) {
			flagStr = "ok";
		}
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sp.edit();
		editor.putString("bind_flag", flagStr);
		editor.commit();
	}

	public static List<String> getTagsList(String originalText) {
		if (originalText == null || originalText.equals("")) {
			return null;
		}
		List<String> tags = new ArrayList<String>();
		int indexOfComma = originalText.indexOf(',');
		String tag;
		while (indexOfComma != -1) {
			tag = originalText.substring(0, indexOfComma);
			tags.add(tag);

			originalText = originalText.substring(indexOfComma + 1);
			indexOfComma = originalText.indexOf(',');
		}

		tags.add(originalText);
		return tags;
	}

	public static String getLogText(Context context) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sp.getString("log_text", "");
	}

	public static void setLogText(Context context, String text) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sp.edit();
		editor.putString("log_text", text);
		editor.commit();
	}

	/**
	 * 检测是否联网
	 * 
	 * @param context
	 *            上下文环境
	 * @return false 未连 true 连接
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			return false;
		} else {
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 *            手机号码
	 * @return 返回Boolean型，true为合法，false为不合法
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((\\(\\d{3}\\))|(\\d{3}\\-))?1[0-9][0-9]\\d{8}");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 获取字符串的长度，如果有中文，则每个中文字符计为2位
	 *
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 */
	public static int chineseLength(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/**
	 * 截取字符串
	 *
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 */
	public static String substring(String value, int start, int end) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		StringBuffer buffer = new StringBuffer();
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		int tempLength = end > value.length() ? value.length() : end + 1;
		for (int i = start; i < tempLength; i++) {
			if (i + 1 < value.length()) {
				/* 获取一个字符 */
				String temp = value.substring(i, i + 1);
				/* 判断是否为中文字符 */
				if (temp.matches(chinese)) {
					/* 中文字符长度为2 */
					valueLength += 2;
				} else {
					/* 其他字符长度为1 */
					valueLength += 1;
				}
				if (valueLength <= (end - start)) {
					buffer.append(temp);
				}
			}
		}
		return buffer.toString();
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "1.0";
		}
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public static int getVersionCode(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			int version = info.versionCode;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * 根据原图和变长绘制圆形图片
	 * 
	 * @param source
	 * @param min
	 * @return
	 */
	public static Bitmap createCircleImage(Bitmap source, int min) {
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		Bitmap target = Bitmap.createBitmap(min, min, Config.ARGB_8888);
		/**
		 * 产生一个同样大小的画布
		 */
		Canvas canvas = new Canvas(target);
		/**
		 * 首先绘制圆形
		 */
		canvas.drawCircle(min / 2, min / 2, min / 2, paint);
		/**
		 * 使用SRC_IN
		 */
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		/**
		 * 绘制图片
		 */
		canvas.drawBitmap(source, 0, 0, paint);
		return target;
	}

	public static boolean isEmail(String content) {
		return content
				.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	}

	public static int getStatusBarHeight(Context context) {
		Rect frame = new Rect();
		((Activity) context).getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(frame);
		return frame.top;
	}

	public static int getTitleBarHeight(Context context) {
		int contentTop = ((Activity) context).getWindow()
				.findViewById(Window.ID_ANDROID_CONTENT).getTop();
		// statusBarHeight是上面所求的状态栏的高度
		return contentTop - getStatusBarHeight(context);
	}

	/**
	 * encodeBase64File:(将文件转成base64 字符串). <br/>
	 * 
	 * @author guhaizhou@126.com
	 * @param path
	 *            文件路径
	 * @return
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public static String encodeBase64File(String path) throws Exception {
		File file = new File(path);
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		return Base64.encodeToString(buffer, Base64.DEFAULT);
	}

	/**
	 * encodeBase64File:(将文件转成base64 字符串). <br/>
	 * 
	 * @author guhaizhou@126.com
	 * @param file
	 *            文件
	 * @return
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public static String encodeBase64File(File file) {
		try {
			FileInputStream inputFile = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			inputFile.close();
			return Base64.encodeToString(buffer, Base64.DEFAULT);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * add shadow to bitmap
	 *
	 * @param originalBitmap
	 * @return
	 */
	public static Bitmap drawImageDropShadow(Bitmap originalBitmap) {

		BlurMaskFilter blurFilter = new BlurMaskFilter(10,
				BlurMaskFilter.Blur.NORMAL);
		Paint shadowPaint = new Paint();
		shadowPaint.setAlpha(50);
		shadowPaint.setColor(0xff000000);
		shadowPaint.setMaskFilter(blurFilter);

		int[] offsetXY = new int[2];
		Bitmap shadowBitmap = originalBitmap
				.extractAlpha(shadowPaint, offsetXY);

		Bitmap shadowImage32 = shadowBitmap.copy(Bitmap.Config.ARGB_8888, true);
		Canvas c = new Canvas(shadowImage32);
		c.drawBitmap(originalBitmap, offsetXY[0], offsetXY[1], null);

		return shadowImage32;
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int roundPx) {
		try {
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());
			final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight()));
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(Color.BLACK);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

			final Rect src = new Rect(0, 0, bitmap.getWidth(),
					bitmap.getHeight());

			canvas.drawBitmap(bitmap, src, rect, paint);
			return output;
		} catch (Exception e) {
			return bitmap;
		}
	}

	public static Bitmap drawableToBitamp(Drawable drawable) {
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		// System.out.println("Drawable转Bitmap");
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// 注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap;
	}

	@SuppressWarnings("deprecation")
	public static Drawable bitmapToDrawable(Bitmap bmp) {
		return new BitmapDrawable(bmp);
	}

	public static String md5(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(
					string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}

	// MD5加密，32位
	public static String MD5(String str) {
		StringBuffer hexValue = new StringBuffer();
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");

			char[] charArray = str.toCharArray();
			byte[] byteArray = new byte[charArray.length];

			for (int i = 0; i < charArray.length; i++) {
				byteArray[i] = (byte) charArray[i];
			}
			byte[] md5Bytes = md5.digest(byteArray);

			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return str;
		}
	}

	// 可逆的加密算法
	public static String encryptmd5(String str) {
		char[] a = str.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 'l');
		}
		String s = new String(a);
		return s;
	}

	public static String getSDPath() {
		File sdDir = null;
		if (isExistSD()) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		} else {
			sdDir = Environment.getRootDirectory();
		}
		return sdDir.toString();
	}

	public static String getSDAbsolutePath() {
		String sdDir = null;
		if (isExistSD()) {
			sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();// 获取跟目录
		}
		return sdDir;
	}

	private static boolean isExistSD() {
		return Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	public static String prettyJson(String body) {
		if (TextUtils.isEmpty(body)) {
			return body;
		}
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			StringWriter stringWriter = new StringWriter();
			JsonWriter jsonWriter = new JsonWriter(stringWriter);
			jsonWriter.setIndent("\u00A0\u00A0");
			JsonElement jsonElement = new JsonParser().parse(body);
			gson.toJson(jsonElement, jsonWriter);
			return stringWriter.toString();
		} catch (Exception e) {
			return body;
		}
	}

	/**获取屏幕的尺寸
	 * @param context 上下文*/
	public static int[] getScreenScale(Context context){
		DisplayMetrics dm = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int scale[] = new int[2];
		scale[0] = dm.widthPixels;
		scale[1] = dm.heightPixels;
		return scale;
	}

	public static long getTimestamp(String arg0) {
		if (TextUtils.isEmpty(arg0))
			return  System.currentTimeMillis();
		long rand = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			Date d2 = sdf.parse(arg0);//将String to Date类型
			rand = d2.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rand;
	}

}

