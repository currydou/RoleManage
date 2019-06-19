package com.example.administrator.rolemanage.constant.type;

/**
 * Created by next on 2018/8/15.
 * 发票查验状态
 */

public interface CheckStatus {

    int VALIDATE_STATUS_UNCHECKED = 0;//0:未检验
    int VALIDATE_STATUS_PASS = 1;//1:验证通过
    int VALIDATE_STATUS_CHECK_OVER_LIMIT = 2;//2:查验次数超限
    int VALIDATE_STATUS_NO_INVOICE = 3;// 3:查无此票
    int VALIDATE_STATUS_INCONSISTENCY = 4;//4:发票信息不一致
    int VALIDATE_STATUS_OTHER_ERROR = 5;// 5:其它错误
    int VALIDATE_STATUS_OTHER_PARAMETER_INVALIDATE = 6;//6 参数无效

    /**
     * 4：购方信息与已存的抬头信息不符
     * 3：纳税识别号不匹配
     * 2：公司名称不匹配
     * 0：抬头未校验
     */

    int RISE_STATUS_INVOICE_TOP_INVALIDATE = 0;
    int RISE_STATUS_COMPANY_NAME_INCONSISTENT = 2;
    int RISE_STATUS_TAX_NUMBER_INCONSISTENT = 3;
    int RISE_STATUS_BUYER_INCONSISTENT = 4;

}
