package com.example.administrator.rolemanage.constant.type;

//0是未认证，1为认证中，2已认证，3为已拒绝
public interface InvoiceTopCertificationStatus {
    int UNCERTIFIED = 0;
    int IN_CERTIFICATION = 1;
    int CERTIFIED = 2;
    int REJECTED = 3;
}

