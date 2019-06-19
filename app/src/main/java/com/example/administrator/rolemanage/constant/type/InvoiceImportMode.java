package com.example.administrator.rolemanage.constant.type;

/**
 * 发票导入方式
 */
public interface InvoiceImportMode {

    int SOURCE_TYPE_MANUAL_IMPORT = 0;//0人工导入
    int SOURCE_TYPE_SCAN_IMPORT = 1;//1发票扫描
    int SOURCE_TYPE_WECHAT_IMPORT = 2;//2.微信导入
    int SOURCE_TYPE_EMAIL_IMPORT = 3;//3.邮箱导入
    int SOURCE_TYPE_ALIPAY_IMPORT = 4;// 4.支付宝
    int SOURCE_TYPE_JD_IMPORT = 5;//5:京东
    int SOURCE_TYPE_OCR = 6;//OCR
    int SOURCE_TYPE_MIN_APP = 7;//微信小程序

}
