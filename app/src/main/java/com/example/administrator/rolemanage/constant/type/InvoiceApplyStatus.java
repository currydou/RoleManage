package com.example.administrator.rolemanage.constant.type;

/**
 * Created by next on 2018/1/30.
 * 发票报销状态
 */

public interface InvoiceApplyStatus {

    /** * 未报帐 */
    int APPLY_STATUS_NO_REIMBURSE = 0;
    /** * 提交中 */
    int APPLY_STATUS_SUBMITTED = 1;
    /** * 审批中 */
    int APPLY_STATUS_REIMBURSING = 2;
    /** *   */
    int APPLY_STATUS_BILL_ACCOUNT_WAIT = 6;
    /** * 已报账 */
    int APPLY_STATUS_BILL_ACCOUNT_HAS = 7;
    /** * 已撤销 */
    int APPLY_STATUS_BILL_ACCOUNT_CANCEL = 8;
    /** * 已支付 */
    int APPLY_STATUS_BILL_ACCOUNT_PAID = 9;
    /** * 赠送中 */
    int APPLY_STATUS_GIVING = 10;

}
