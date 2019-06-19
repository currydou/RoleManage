package com.example.administrator.rolemanage.constant.type;

/**
 * 报账单报销状态
 */

public interface BillAccountApplyStatus {

    /*报账单状态：
        0保存
        6审批中也就是报账中
        7完结
        8撤销*/

    /** 保存，未报销（自己） */
    int APPLY_STATUS_APPLY_HOLD = 0;//
    /** 同意报销（） */
    int APPLY_STATUS_APPLY_PASS = 1;//
    /** 拒绝报销 */
    int APPLY_STATUS_APPLY_DENY = 2;//
    /** 审批中（） */
    int APPLY_STATUS_APPLY_WAIT = 6;//
    /** 报销结束 */
    int APPLY_STATUS_APPLY_FINISHED = 7;//
    /** 报销被取消 */
    int APPLY_STATUS_APPLY_CANCEL = 8;//
    /** 报账已支付 */
    int APPLY_STATUS_PAID = 9;//

}
