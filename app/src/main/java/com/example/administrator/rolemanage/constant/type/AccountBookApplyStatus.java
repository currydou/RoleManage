package com.example.administrator.rolemanage.constant.type;

/**
 * Created by next on 2018/1/30.
 * 记账报销状态
 */

public interface AccountBookApplyStatus {
    /*
        记账的状态：
        -1  未使用（可删）
        0   已使用（不可删）
        6   提交审批（不可删）
        7   完结（不可删）
        8   撤销（不可删）
        */

    int APPLY_STATUS_NOT_USED = -1;
    int APPLY_STATUS_IN_USE = 0;
    int APPLY_STATUS_BILL_ACCOUNT_APPROVING = 6;
    int APPLY_STATUS_BILL_ACCOUNT_FINISHED = 7;
    int APPLY_STATUS_BILL_ACCOUNT_CANCEL = 8;
    int APPLY_STATUS_BILL_ACCOUNT_PAID = 9;

}
