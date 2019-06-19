package com.example.administrator.rolemanage.constant;

/**
 * Created by next on 2018/8/15.
 */

public interface InputLengthConstant {
    //用户名称
    int MIN_NAME_LENGTH = 2;
    int MAX_NAME_LENGTH = 15;
    int MAX_TEL_LENGTH = 11;
    //团队名称
    int MAX_TEAM_NAME_LENGTH = 15;
    int MIN_TEAM_NAME_LENGTH = 2;
    //密码
    int MIN_PWD_LENGTH = 6;
    int MAX_PWD_LENGTH = 30;
    //报账单
    int MAX_RECEIVER_LENGTH = 50;//收款方最大长度
    int MAX_OPEN_BANK_LENGTH = 100;//开户行最大长度
    int MAX_RECEIVE_ACCOUNT_LENGTH = 20;//开户账号最大长度
    int MAX_RECEIVE_REMARK_LENGTH = 500;//收款备注最大长度
    int MAX_DEPARTMENT_LENGTH = 50;//入账部门最大长度
    int MAX_PROJECT_LENGTH = 50;//入账项目最大长度
    int MAX_COMMENT_LENGTH = 500;//评论最大长度
    int MIN_COMMENT_LENGTH = 2;//评论最小长度
    int MIN_BILL_ACCOUNT_REMARK_LENGTH = 2;
    int MAX_BILL_ACCOUNT_REMARK_LENGTH = 500;
    //快速记账
    int MAX_MONEY = 100000000;//小于等于一个亿
    int MAX_MONEY_LENGTH = 9;//最大位数
    int MAX_QUICK_ACCOUNT_REMARK_LENGTH = 500;
    int MIN_QUICK_ACCOUNT_REMARK_LENGTH = 1;
    //抬头
    int COMPANY_NAME_MAX_LENGTH = 50;

}
