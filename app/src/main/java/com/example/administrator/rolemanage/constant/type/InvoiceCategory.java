package com.example.administrator.rolemanage.constant.type;

/**
 * Created by next on 2018/8/15.
 * 发票种类
 */

public interface InvoiceCategory {

    //增票
    String INVOICE_TYPE_TAX_SPECIAL = "10";//增值税专用发票
    String INVOICE_TYPE_GENERAL_TAX_SPECIAL = "20";//增值税普通发票
    String INVOICE_TYPE_TAX_DIGIT = "30";//增值税电子普通发票
    String INVOICE_TYPE_TAX_SLICE = "60";//增值税卷票

    String INVOICE_TYPE_FIXED = "70";//定额发票
    String INVOICE_TYPE_TRAIN = "80";//火车票
    String INVOICE_TYPE_PLANE = "90";//机票
    String INVOICE_TYPE_GENERAL = "100";//通用机打票
    String INVOICE_TYPE_HIGH_SPEED_TRAIN = "110";//高铁、动车
    String INVOICE_TYPE_TAXI = "120";//出租车
    String INVOICE_TYPE_SUBWAY = "130";//地铁
    String INVOICE_TYPE_SMALL = "140";//消费小票
    String INVOICE_TYPE_BUS = "150";//汽车票
    //40：货物运输业增值税专用发票
    //50：机动车销售统一发票
}
