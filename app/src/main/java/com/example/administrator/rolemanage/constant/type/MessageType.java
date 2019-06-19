package com.example.administrator.rolemanage.constant.type;


public interface MessageType {

    /**
     * 父类消息类型
     * 1 系统消息  2 加入企业成功  3 加入企业失败  4退出企业  5发票报销失败  6发票  7报账单  8团队
     * 9微币变动    10索票结果
     */
    interface SuperMessageType {
        int SYSTEM = 1;
        int JOIN_BUSSINESS_SUCCESS = 2;
        int JOIN_BUSINESS_FAIL = 3;
        int EXIT_BUSINESS = 4;
        int INVOICE_REIMBURSE_FAIL = 5;
        int INVOICE = 6;
        int BILL_ACCOUNT = 7;
        int TEAM = 8;
        int WECOIN_CHANGE = 9;
        int ASK_FOR_INVOICE_RESULT = 10;
    }

    /**
     * 子类消息类型
     * 10系统
     * 60发票导入成功     61发票导入电子发票成功   62发票导入已存在   63发票导入错误    64发票导入未查验电子发票   65发票导入已占用
     * 70审批同意拒绝     71审批节点收到评论       72评论回复         73邮件
     * 80团队成员加入     81团队成员退出团队       82团队名称更改     83团队成员被移除团队   84团队移交权限)
     */

    interface SubMessageType {
        int SYSTEM = 10;
        //
        int INVOICE_IMPORT_SUCCESS = 60;
        int E_INVOICE_IMPORT_SUCCESS = 61;
        int INVOICE_IMPORT_EXISTED = 62;
        int INVOICE_IMPORT_FAIL = 63;
        int E_INVOICE_IMPORT_UNCHECKED = 64;
        int INVOICE_IMPORT_OCCUPIED = 65;
        //
        int APPROVE_AGREE_REJECT = 70;
        int APPROVAL_NODE_RECEIVE_COMMENTS = 71;
        int COMMENTS_REPLY = 72;
        int EMAIL = 73;
        //
        int TEAM_MEMBER_JOIN = 80;
        int TEAM_MEMBER_EXIT = 81;
        int TEAM_MEMBER_NAME_MODIFICATION = 82;
        int TEAM_MEMBER_REMOVED = 83;
        int TEAM_MEMBER_TRANSFER_AUTHORITY = 84;
        //
        int WECOIN_CHANGE = 90;
        //
        int ASK_FOR_INVOICE_RESULT = 100;
    }

}
