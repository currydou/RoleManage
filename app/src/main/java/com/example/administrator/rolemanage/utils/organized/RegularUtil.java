package com.example.administrator.rolemanage.utils.organized;

/**
 * Created by next on 2018/4/18.
 */

public class RegularUtil {

    //包含字母、汉字、·、空格 ，一个或多个
    public static final String NAME_REGULAR = "[a-zA-Z\\u4e00-\\u9fa5· ]+";
    public static final String COMPANY_REGULAR = "[a-zA-Z\\u4e00-\\u9fa5]+";
    public static final String TEAM_NAME_REGULAR = "[a-zA-Z\\u4e00-\\u9fa5\\d]+";

    // 8  10/12
    public static boolean atpMatcher1(String str, int singleLength) {
        String pattern1 = "\\d{" + singleLength + "}"; //只能是数字0-9，指定整数位长度
//        String str = "12345678";
        return str.matches(pattern1);
    }

    //8.2  8  8.
    public static boolean atpMatcher2(String str, int singleLength, int decimalLength) {
        String pattern1 = "\\d{" + singleLength + "}";
        String pattern2 = pattern1 + "\\.\\d{0," + decimalLength + "}|"  //只能是数字0-9 . ，指定整数位长度和小数位范围（小数位长度必须明确）
                + pattern1;
//        String str = "12345678.";
        return str.matches(pattern2);
    }

    public static boolean atpMatcher21(String str, int singleLength, String decimalLength) {
        String pattern1 = "\\d{" + singleLength + "}";
        String pattern2 = pattern1 + "\\.\\d{0," + decimalLength + "}|"  //只能是数字0-9，指定整数位长度（小数位长度可以为空串，即无限长）
                + pattern1;
//        String str = "12345678.";
        return str.matches(pattern2);
    }

    public static boolean atpMatcher22(String str, String decimalLength) {
        String pattern1 = "\\d*";
        String pattern2 = pattern1 + "\\.\\d{0," + decimalLength + "}|"  //只能是数字0-9 . ，整数位长度不定，指定小数位范围（小数位长度可以为空，即无限长）
                + pattern1;
//        String str = "12345678.";
        return str.matches(pattern2);
    }

    public static boolean atpMatcher23(String str, int singleLength, String decimalLength) {
        String pattern1 = "\\d{0," + singleLength + "}";
        String pattern2 = pattern1 + "\\.\\d{0," + decimalLength + "}|"  //只能是数字0-9，指定整数位长度0-X（小数位长度可以为空串，即无限长）
                + pattern1;
//        String str = "12345678.";
        return str.matches(pattern2);
    }

    public static boolean atpMatcher24(String str, int singleLength, String decimalLength) {
        String pattern1 = "{0," + singleLength + "}";
        String pattern2 = pattern1 + "\\.\\d{0," + decimalLength + "}|"  //随意输入，指定整数位长度0-X（小数位长度可以为空串，即无限长）
                + pattern1;
//        String str = "12345678.";
        return str.matches(pattern2);
    }

    // 10-12
    public static boolean atpMatcher3(String str, int integerMinLength, int integerMaxLength) {
        String pattern3 = "\\d{" + integerMinLength + "," + integerMaxLength + "}"; //只能是数字0-9，指定整数位数范围
//        String str = "1234";
        return str.matches(pattern3);
    }


    //  10-12.2
    public static boolean atpMatcher4(String str, int integerMinLength, int integerMaxLength, String decimalLength) {
        String pattern3 = "\\d{" + integerMinLength + "," + integerMaxLength + "}";
        String pattern4 = pattern3 + "\\.\\d{0," + decimalLength + "}|" //只能是数字0-9，指定整数位数范围和小数位范围
                + pattern3;
//        String str = "1.";
        return str.matches(pattern4);
    }

    public static boolean atpMatcher41(String str, int integerMinLength, int integerMaxLength, String decimalLength) {
        String pattern3 = "\\d{" + integerMinLength + "," + integerMaxLength + "}";
        String pattern4 = pattern3 + "\\.\\d{0," + decimalLength + "}|" //只能是数字0-9，指定整数位数范围和小数位范围
                + pattern3;
//        String str = "1.";
        return str.matches(pattern4);
    }


    // 1    1.  1.1
    public static boolean atpMatcher5(String str, String decimalLength) {
        String pattern5 = "\\d{0,}"; ////只能是数字0-9，数字长度不限制
        String pattern6 = pattern5 + "\\.\\d{0," + decimalLength + "}|" //只能是数字0-9，整数位不限制，小数位指定范围
                + pattern5;
//        String str = "1.111";
        return str.matches(pattern6);
    }


}
