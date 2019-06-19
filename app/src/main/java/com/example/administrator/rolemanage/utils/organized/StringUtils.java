package com.example.administrator.rolemanage.utils.organized;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.example.administrator.rolemanage.BuildConfig;
import com.example.administrator.rolemanage.base.FeiKongBaoApplication;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Libin on 2016/8/1.
 * 字符串处理类
 */
public class StringUtils {

    //hh 12小时制；HH 24小时制
    private static final String TAG = StringUtils.class.getSimpleName();
    public static SimpleDateFormat sdf_yyyy___MM___dd_T_HH_mm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());//加上后面的时分，否则解析不出来

    public static SimpleDateFormat sdf_yyyy___MM___dd_HH_mm = new SimpleDateFormat("yyyy-MM-dd.HH:mm", Locale.getDefault());
    public static SimpleDateFormat sdf_yyyy_MM__dd_HH_mm = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    public static SimpleDateFormat sdf_yyyy_year_MM_month_dd_day = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
    public static SimpleDateFormat sdf_yyyy___MM___dd_ = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static SimpleDateFormat sdf_yyyy___MM___dd_T = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static SimpleDateFormat sdf_yyyy_MM_dd = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    public static SimpleDateFormat sdf_yyyy_dot_MM_dot_dd = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
    public static SimpleDateFormat sdf_yyyy___MM_ = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
    public static SimpleDateFormat sdf_yyyy_year_MM_month = new SimpleDateFormat("yyyy年MM月", Locale.getDefault());
    public static SimpleDateFormat sdf_year_MM_dd = new SimpleDateFormat("MM月dd日", Locale.getDefault());
    public static SimpleDateFormat sdf__MM__dd_HH_mm = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
    public static SimpleDateFormat sdf_MM_dd_HH_mm = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());
    public static SimpleDateFormat sdf_MM_dd = new SimpleDateFormat("MM-dd", Locale.getDefault());
    public static SimpleDateFormat sdf_MM_dot_dd = new SimpleDateFormat("MM.dd", Locale.getDefault());
    public static SimpleDateFormat sdf_HH_mm = new SimpleDateFormat("HH:mm", Locale.getDefault());
    public static SimpleDateFormat SDF_FILE_TAG = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault());

    static {
        //设置0时区
//        sdf_yyyy___MM___dd_T_HH_mm.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        //统一时区
        sdf_yyyy___MM___dd_T_HH_mm.setTimeZone(TimeZone.getTimeZone("UTC"));
        sdf_yyyy___MM___dd_T.setTimeZone(TimeZone.getTimeZone("UTC"));
        sdf_MM_dot_dd.setTimeZone(TimeZone.getTimeZone("GMT+8"));//Greenwich Mean Time 格林尼治标准时间 , 东八区（北京时间）
    }

    //字符串转double
    public static double getDobFromStr(String str) {
        if (isEmpty(str) || (!isDecimal(str) && !isWholeNumber(str))) {
            return 0.0;
        } else {
            return Double.valueOf(str);
        }
    }

    public static String getMoney(String str) {
        if (isEmpty(str) || (!isDecimal(str) && !isWholeNumber(str))) {
            return "0.00";
        } else {
            String result = new DecimalFormat("0.00").format(Double.valueOf(str));
            return result;
        }
    }


    private static boolean isMatch(String regex, String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }


    public static String getDetailMobileId() {
//        return PerfUtil.getString(Constant.SP_UID) + new Date().getTime();
        return UUID.randomUUID().toString();
    }

    public static String getInvoiceDate(String dateStr) {
        if (TextUtils.isEmpty(dateStr)) return "";
        SimpleDateFormat sdf;
        if (dateStr.contains("年")) {
            sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
        } else if (dateStr.contains("-")) {
            sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        } else if (dateStr.contains("/")) {
            sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        } else {
            sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Date d;
        try {
            d = sdf.parse(dateStr);
            return sdf2.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 同一个字段，后台返回的格式不一样
     * @param dateStr
     * @param outSdf    null 返回时间戳
     * todo2 待优化：解析的格式只有年月日，没有加时分秒的格式;yyyyMMdd格式
     * @return
     */
    public static String getInvoiceDate2(String dateStr, SimpleDateFormat outSdf) {
        if (TextUtils.isEmpty(dateStr)) return dateStr;
        SimpleDateFormat sdf;
        Date d;
        if (dateStr.contains("年")) {
            sdf = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
        } else if (dateStr.contains("-")) {
            sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        } else if (dateStr.contains("/")) {
            sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        } else if (dateStr.contains("T")) {
            sdf = StringUtils.sdf_yyyy___MM___dd_T_HH_mm;
        } else if (dateStr.length() == 8) {//yyyyMMdd
            sdf = StringUtils.sdf_yyyy_MM_dd;
        } else {//当成时间戳处理
            if (outSdf == null) {
                return dateStr;
            }
            return StringUtils.timeStampToFormat(dateStr, outSdf);
        }
        try {
            d = sdf.parse(dateStr);
            if (outSdf == null) {
                return String.valueOf(d.getTime());
            }
            return outSdf.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;
        }
    }

    public static String getDateBetweenCurrentDate(int days) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        Calendar c = Calendar.getInstance(); // 当时的日期和时间
        int day = c.get(Calendar.DAY_OF_MONTH) - days;
        c.set(Calendar.DAY_OF_MONTH, day);
        String defaultdateStartDate = df.format(c.getTime());
        return defaultdateStartDate;
    }

    public static String[] array = new String[]{"144031539110", "131001570151", "133011501118", "111001571071", "111001471071", "111001471072", "111001571072", "111001671071", "111001671072"};

    public static int checkInvoiceCode(String code) {
        int status = 0;//0:不显示,1:是否设置发票金额,2:是否设置发票校验码(取校验码后六位)
        if (!TextUtils.isEmpty(code)) {
            String result = parseCode(code);
            if (TextUtils.equals(result, "01") || TextUtils.equals(result, "02") || TextUtils.equals(result, "03")) {
                status = 1;
            } else if (TextUtils.equals(result, "04") || TextUtils.equals(result, "10") || TextUtils.equals(result, "11")) {
                status = 2;
            }
        }
        return status;
    }

    public static String parseCode(String code) {
        String c = "99";
        String b = "";
        String b1 = "";
        String b2 = "";
        if (code.length() == 10) {
            b = code.substring(7, 8);
            if (BuildConfig.DEBUG) {
                Log.e("xxxxx10", b1 + ":" + b2 + ":" + b + ":");
            }
            if (TextUtils.equals(b, "1") || TextUtils.equals(b, "5")) {
                c = "01";
                return c;
            } else if (TextUtils.equals(b, "6") || TextUtils.equals(b, "3")) {
                c = "04";
                return c;
            } else if (TextUtils.equals(b, "7") || TextUtils.equals(b, "2")) {
                c = "02";
                return c;
            }
        } else if (code.length() == 12) {
            b1 = code.substring(0, 1);
            b2 = code.substring(10, 12);
            b = code.substring(7, 8);
            if (BuildConfig.DEBUG) {
                Log.e("xxxxx12", b1 + ":" + b2 + ":" + b + ":");
            }
            if (TextUtils.equals(code.substring(1, 5), "3100") && TextUtils.equals(code.substring(7, 10), "701")) {
                c = "10";
                return c;
            } else if (TextUtils.equals(code.substring(1, 3), "33") && TextUtils.equals(b2, "18")) {
                c = "10";
                return c;
            } else if (TextUtils.equals(code.substring(1, 5), "4403") && TextUtils.equals(b2, "18")) {
                if ((TextUtils.equals(code.substring(5, 7), "15") && TextUtils.equals(code.substring(7, 9), "39"))
                        || (TextUtils.equals(code.substring(5, 7), "16") && TextUtils.equals(code.substring(10, 12), "11"))) {
                    c = "10";
                    return c;
                }
            }
            for (int i = 0; i < array.length; i++) {
                if (TextUtils.equals(code, array[i])) {
                    c = "10";
                    return c;
                }
            }
            if (TextUtils.equals(c, "99") && TextUtils.equals(b1, "0") && TextUtils.equals(b2, "11")) {
                c = "10";
                return c;
            }
            if (TextUtils.equals(c, "99")) {
                if (TextUtils.equals(b, "2")) {
                    c = "03";
                    return c;
                } else {
                    c = "11";
                    return c;
                }
            }
        }
        return c;
    }


    private static String[] code = new String[]{"144031539110", "131001570151", "133011501118", "111001571071"};

    /**
     * 做查验页面的时候，李博士发的
     * 上面的 之前没有用。
     * invoiceCode  发票代码
     */
    private static String getInvoiceCategory2(String invoiceCode) {
        String b;
        String c = "99";
        if (invoiceCode.length() == 12) {
            b = invoiceCode.substring(7, 8);

            for (int i = 0; i < code.length; i++) {
                if (TextUtils.equals(invoiceCode, code[i])) {
                    c = "10"; // VAT电子票
                    StringUtils.d(TAG, "10 VAT电子票");
                    break;
                }
            }
            if (TextUtils.equals(c, "99")) {
                //增加判断，判断是否为新版电子票
                if (TextUtils.equals(invoiceCode.charAt(0) + "", "0") && TextUtils.equals(invoiceCode.substring(10, 12), "11")) {
                    c = "10";
                    StringUtils.d(TAG, "10 新版电子票");
                }
                if (invoiceCode.charAt(0) == '0' && (TextUtils.equals(invoiceCode.substring(10, 12), "04") || TextUtils.equals(invoiceCode.substring(10, 12), "05"))) {
                    c = "04";
                    StringUtils.d(TAG, "04 普票");
                }
                if (invoiceCode.charAt(0) == '0' && ("06".equals(invoiceCode.substring(10, 12)) || "07".equals(invoiceCode.substring(10, 12)))) {
                    //判断是否为卷式发票  第1位为0且第11-12位为06或07
                    c = "11";
                    StringUtils.d(TAG, "11 卷式发票'");
                }
                if (invoiceCode.charAt(0) == '0' && "12".equals(invoiceCode.substring(10, 12))) {
                    c = "14";
                    StringUtils.d(TAG, "14 通行费");
                }
            }
            if ("99".equals(c)) {
                //如果还是99，且第8位是2，则是机动车发票
                if (TextUtils.equals(b, 2 + "") && invoiceCode.charAt(0) != '0') {
                    c = "03"; // 机动车发票
                    StringUtils.d(TAG, "03 机动车发票");
                }
                if ("17".equals(invoiceCode.substring(10, 12)) && invoiceCode.charAt(0) == '0') {
                    c = "15";
                    StringUtils.d(TAG, "15");
                }
            }
        } else if (invoiceCode.length() == 10) {
            b = invoiceCode.substring(7, 8);
            if (TextUtils.equals(b, 1 + "") || TextUtils.equals(b, 5 + "")) {
                c = "01";
                StringUtils.d(TAG, "01 专票");
            } else if (TextUtils.equals(b, 6 + "") || TextUtils.equals(b, 3 + "")) {
                c = "04"; // VAT普票
                StringUtils.d(TAG, "04 普票");
            } else if (TextUtils.equals(b, 7 + "") || TextUtils.equals(b, 2 + "")) {
                c = "02";
                StringUtils.d(TAG, "02 机动车发票");
            }
        }
        return c;
    }



    public static SpannableStringBuilder registerSpannableString(String str1, int color1, float size1) {
        if (TextUtils.isEmpty(str1)) {
            return new SpannableStringBuilder();
        }
        SpannableStringUtils.SpanText firstText = new SpannableStringUtils.SpanText(str1,
                getColorById(color1), size1);
        return SpannableStringUtils.makeSpannableString(firstText);
    }

    public static double numberRetainTwo(double number) {
        BigDecimal bigDecimal = new BigDecimal(number);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static void d(String TAG, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static SpannableStringBuilder registerSpannableString(String str1,
                                                                 String str2, int color1, int color2, float size1, float size2) {
        if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2)) {
            return new SpannableStringBuilder();
        }
        SpannableStringUtils.SpanText firstText = new SpannableStringUtils.SpanText(str1,
                getColorById(color1), size1);
        SpannableStringUtils.SpanText secondText = new SpannableStringUtils.SpanText(str2,
                getColorById(color2), size2);
        return SpannableStringUtils.makeSpannableString(firstText, secondText);
    }

    public static SpannableStringBuilder registerSpannableString(String str1,
                                                                 String str2,
                                                                 String str3,
                                                                 int color1,
                                                                 int color2,
                                                                 int color3,
                                                                 float size1,
                                                                 float size2,
                                                                 float size3) {
        if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return new SpannableStringBuilder();
        }
        SpannableStringUtils.SpanText firstText = new SpannableStringUtils.SpanText(str1,
                getColorById(color1), size1);
        SpannableStringUtils.SpanText secondText = new SpannableStringUtils.SpanText(str2,
                getColorById(color2), size2);
        SpannableStringUtils.SpanText thirdText = new SpannableStringUtils.SpanText(str3,
                getColorById(color3), size3);
        return SpannableStringUtils.makeSpannableString(firstText, secondText, thirdText);
    }

    public static int getColorById(int cId) {
        return FeiKongBaoApplication.instance.getResources().getColor(cId);
    }

    public static String getVersion() {
        PackageManager packageManager = FeiKongBaoApplication.instance.getPackageManager();
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(
                    FeiKongBaoApplication.instance.getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 数据sha1签名
     *
     * @param str
     * @return
     */
    public static String getSha1(String str) {
        if (null == str || 0 == str.length()) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }


    /*=================================================================================================*/


    /**
     * 自动生成32位的UUid，对应数据库的主键id进行插入用。
     *
     * @return
     */
    public static String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static boolean isEmpty(String string) {
        return (string == null) || (string.length() == 0);
    }

    public static boolean isPositiveInteger(String orginal) {
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
    }

    public static boolean isNegativeInteger(String orginal) {
        return isMatch("^-[1-9]\\d*", orginal);
    }


    public static boolean isWholeNumber(String orginal) {
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
    }

    public static boolean isDecimal(String orginal) {
        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
    }

    /**
     * 判断字符串为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^-?[0-9]+");
        return pattern.matcher(str).matches();
    }


    /**
     * 字符串转double,(默认两位小数,pointRule 传null)
     */
    public static double getDobFromStr(String str, String pointRule) {
        if (isEmpty(str) || (!isDecimal(str) && !isWholeNumber(str))) {
            return 0.0;
        } else {
            if (isEmpty(pointRule)) {
                pointRule = "#.00";
            }
            String result = new DecimalFormat(pointRule).format(Double.valueOf(str));
            return Double.valueOf(result);
        }
    }

    /**
     * 不使用科学计数法
     */
    public static double getDobFromStr2(Double d) {
        if (isEmpty(d + "")) {
            return 0.00;
        } else {
            String pointRule = "00.00";
            double value = 0.00;
            try {
                String result = new DecimalFormat(pointRule).format(d);
                value = Double.valueOf(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return value;
        }
    }

    /**
     * 保留两位小数
     * 不使用科学计数法
     * @param d
     * @return
     */
    public static String getDobFromStr3(Double d) {
        if (d == null) {
            return "";
        }
        NumberFormat nf = NumberFormat.getInstance();
        //设置保留多少位小数
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        // 取消科学计数法
        nf.setGroupingUsed(false);
        //返回结果
        return nf.format(d);
    }

    //字符串转Int
    public static int getIntFromStr(String str) {
        if (isEmpty(str) || !isWholeNumber(str)) {
            return 0;
        } else {
            return Integer.valueOf(str);
        }
    }

    /**
     * 得到特定格式的当前时间戳
     * yyyy-MM-dd 的当前时间戳和 yyyy-MM 的当前时间戳 的大小是不同的
     *
     * @param sdf
     * @return
     */
    public static long getCurrentTimeStamp(SimpleDateFormat sdf) {
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        if (sdf == null) {
            return timeInMillis;
        }
        String format = sdf.format(timeInMillis);
        Date date;
        try {
            date = sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
            return timeInMillis;
        }
        return date.getTime();
    }

    /**
     * 得到特定格式时间的对应的时间戳
     *
     * @param dateStr
     * @param sdf
     * 异常情况返回当前时间戳
     * @return
     */
    public static long formatTimeToTimeStamp(String dateStr, SimpleDateFormat sdf) {
        if (TextUtils.isEmpty(dateStr) || sdf == null) {
            return Calendar.getInstance().getTimeInMillis();
        }
        try {
            Date date = sdf.parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return Calendar.getInstance().getTimeInMillis();
        }
    }

    /**
     * 时间戳转换成特定格式的时间
     * @param timeStamp
     * @param sdf
     * @return
     */
    public static String timeStampToFormat(String timeStamp, SimpleDateFormat sdf) {
        if (sdf == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        try {
            if (TextUtils.isEmpty(timeStamp)) {
                return sdf.format(calendar.getTimeInMillis());
            }
            return sdf.format(Long.valueOf(timeStamp));
        } catch (Exception e) {
            e.printStackTrace();
            return sdf.format(calendar.getTimeInMillis());
        }
    }

    public static String timeStampToFormat(long timeStamp, SimpleDateFormat sdf) {
        if (sdf == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        try {
            if (timeStamp == 0) {
                return sdf.format(calendar.getTimeInMillis());
            }
            return sdf.format(timeStamp);
        } catch (Exception e) {
            e.printStackTrace();
            return sdf.format(calendar.getTimeInMillis());
        }
    }

    /**
     * 将format1格式的时间转成format2格式的时间
     *
     * @param dateStr
     * @param sdf1
     * @param sdf2
     * @return
     */
    public static String formatToFormat(String dateStr, SimpleDateFormat sdf1, SimpleDateFormat sdf2) {
        if (TextUtils.isEmpty(dateStr)) {
            return "";
        }
        try {
            Date date = sdf1.parse(dateStr);
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;
        }
    }


    /**
     * 邮箱验证工具
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        boolean flag = true;
        if (email == null) return false;
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
//        Pattern p = Pattern.compile("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
        Matcher m = p.matcher(email);
        boolean b = m.find();
        if (b) {//可以匹配到，看是不是匹配到整个字符串。//@274086765@qq.com 不符合的情况
            String group = m.group();
            if (TextUtils.isEmpty(group)) {
                flag = false;
            } else {
                flag = TextUtils.equals(group, email);
            }
        } else {
            flag = false;
        }
        return flag;
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        String pattern = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        return phoneNumber.matches(pattern);
    }

    public static String decodeBase64ToStr(String base64Str) throws UnsupportedEncodingException {
        byte[] bytes = Base64.decode(base64Str, Base64.DEFAULT);
        return new String(bytes, "utf-8");
    }

    //整数3个逗号隔开，保留两位小数
    public static String commaAndHalfUp(Double d) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        // 是否以逗号隔开, 默认true以逗号隔开,如[123,456,789.128]
        nf.setGroupingUsed(true);
        // 结果未做任何处理
        String format;
        try {
            format = nf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
            format = "";
        }
        return format;
    }

    /**
     * 校验银行卡卡号
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        if (TextUtils.isEmpty(cardId)||cardId.length()<5) {
            return false;
        }
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        return bit != 'N' && cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

}
