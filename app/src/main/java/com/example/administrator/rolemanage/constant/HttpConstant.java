package com.example.administrator.rolemanage.constant;

/**
 * Created by next on 2018/1/11.
 */

public class HttpConstant {


    public static final class HttpKey {
        public static String KEY_UUID = "uuid";
        public static String KEY_APPLY_CODE = "applyCode";
        public static String KEY_ECARD_ID = "ecardId";
        public static String KEY_ACTION = "action";
        public static String KEY_SOURCE_TYPE= "sourceType";
        public static String KEY_STATE= "state";
        public static String KEY_REMARK= "remark";
        public static String KEY_SEND_EMAILS = "sendEmails";
        public static String KEY__ID = "_id";
        public static String KEY_ID = "id";
        public static String KEY_STATUS = "status";
        public static String KEY_APPROVEID = "approveId";
        public static String KEY_KEY_WORD = "keyword";
        public static String KEY_APPLY_SHEET = "applySheet";
        public static String KEY_NODE_ID = "nodeId";
        public static String KEY_REVIEW_ID = "reviewId";
        public static String KEY_BY_REVIEW_ID = "byReviewId";
        public static String KEY_CONTENT = "content";
        public static String KEY_LOGIN_TYPE = "loginType";
        public static String KEY_TEAM_ID = "teamId";
        public static String KEY_TEAM_NAME = "teamName";
        public static String KEY_TEAM_IMG = "teamImg";
        public static String KEY_USERID = "userId";
        public static String KEY_OPTUSERID = "optUserId";
        public static String KEY_TEAMSPREADS = "teamSpreads";
        public static String KEY_COST_TAG_ID = "costTagId";
        public static String KEY_SWITCHS = "switchs";
        public static String KEY_OLD_UPPOSITION = "oldUpPosition";
        public static String KEY_OLD_DNPOSITION = "oldDnPosition";
        public static String KEY_UP_POSITION = "upPosition";
        public static String KEY_DN_POSITION = "dnPosition";
        public static String KEY_FEEFLAG = "feeFlag";
        public static String KEY_NAME = "name";
        public static String KEY_IMG_ID = "imgId";
        public static String KEY_FILE = "file";
        public static String KEY_IMAGE_KEY = "imageKey";
        public static String KEY_IMAGE_URL = "imageUrl";
        public static String KEY_IMAGE_ETAG = "imageETag";
        public static String KEY_DEVICE = "device";
        public static String KEY_CMD = "cmd";
        public static String KEY_SCENE = "scene";
        public static String KEY_PAGE = "page";
        public static String KEY_COMPANY = "company";
        public static String KEY_TITLE = "title";
        public static String KEY_FILE_PATH = "filePath";
        public static String KEY_APPLY_ID = "applyId";
        public static String KEY_CARD_ID = "cardId";
        public static String KEY_TARGET = "target";
        public static String KEY_COMPANY_ID = "companyId";
        public static String KEY_IS_COMPANY = "isCompany";
        public static String KEY_LOGO_URL = "logoUrl";
        public static String KEY_TELEPHONE = "telephone";
        public static String KEY_VALIDATE_STATE = "validateState";
        public static String KEY_RECEIVER_ID = "receiverId";
        public static String KEY_SHARER_ID = "sharerId";
        public static String KEY_CONSUMP_TYPE = "consumpType";
        public static String KEY_COMBO_ID = "comboId";
        public static String KEY_BUT_IS_OK = "butIsOk";
        public static String KEY_BUT_IS_COST_OK = "butIsCostOk";
        public static String KEY_RECORDING_TYPE = "recordingType";
        public static String KEY_ASK_FOR_ID = "askForId";
        public static String KEY_INVOICE_CATEGORY = "invoiceCategory";
        public static String KEY_INVOICE_ID = "invoiceId";
        public static String KEY_OPERATION = "operation";
    }

    public static final class HttpReqKey {
        public static final String PM_TOKEN = "token";
        public static String KEY_TICKET = "ticket";
        public static String KEY_TEL = "tel";
        public static String KEY_ACCESS_TYPE = "accessType";
        public static String KEY_OCR_TYPE = "ocrType";
        public static String KEY_ANDROID = "android";
        public static String KEY_PAGE = "page";
        public static String KEY_PAGE_INDEX = "pageIndex";
        public static String KEY_PAGE_SIZE = "pageSize";
        public static String KEY_TOTAL_ID = "totalId";
        public static String KEY_INVOICE_TYPE = "invoiceType";
        public static String KEY_INVOICE_DATE = "invoiceDate";
        public static String KEY_MONEY = "money";
        public static String KEY_APPLY_MONEY = "applyMoney";
        public static String KEY_COST_TAG = "costTag";
        public static String KEY_IMAGE_URL = "imageUrl";

    }

    public static final class HttpRespKey {
        public static String KEY_STATUS_CODE = "statusCode";
        public static String KEY_MSG = "msg";
        public static String KEY_CODE = "code";
        public static String KEY_DATA = "data";
        public static String KEY_MESSAGE = "message";
        public static String KEY_ORGID = "orgid";
        public static String KEY_ORGNAME = "orgname";
        public static String KEY_LOGO = "logo";


    }


    public static final class HttpCode {
        public static final int CODE_0 = 0; //
        public static final int CODE_1 = 1; //
        public static final int CODE_2 = 2; // token失效，跳到登录界面
        public static final int CODE_200 = 200; // 正常返回｜正确｜成功
        public static final int CODE_500 = 500; // 直接弹出后台的错误提示
        public static final int CODE_90000 = 90000; // 有升级时的code


        /*做微信登录时，新增的*/
        public static final int CODE_10000 = 10000; // 成功
        public static final int CODE_10001 = 10001; // 数据错误                                     提示：攻城狮正在紧急搜索中，请稍后重试！
        public static final int CODE_10002 = 10002; // 没找到该用户
        public static final int CODE_10003 = 10003; // 无效的请求                                   提示：攻城狮正在紧急搜索中，请稍后重试！
        public static final int CODE_10004 = 10004; // 无效的请求                                   提示：攻城狮正在紧急搜索中，请稍后重试！
        public static final int CODE_10005 = 10005; // 网络请求失败
        public static final int CODE_10006 = 10006; //
        public static final int CODE_10007 = 10007; // 无效的登录凭证
        public static final int CODE_10008 = 10008; // 请重新获取微信账号授权
        public static final int CODE_10009 = 10009; // 短消息服务暂时不可用
        public static final int CODE_10010 = 10010; // 不能重复绑定手机号码
        public static final int CODE_10011 = 10011; // 请输入有效手机号码和6位数以上密码            提示：攻城狮正在紧急搜索中，请稍后重试！
        public static final int CODE_10012 = 10012; // 密码不一致
        public static final int CODE_10013 = 10013; // 验证码发送失败
        public static final int CODE_10014 = 10014; // 无效的验证码
        public static final int CODE_10015 = 10015; // 创建新用户失败
        public static final int CODE_10016 = 10016; // 手机号码已被占用,不能重复绑定                提示：攻城狮正在紧急搜索中，请稍后重试！
        public static final int CODE_10017 = 10017; // 微信服务出错
        public static final int CODE_10018 = 10018; // 未绑定任何微信账号
        public static final int CODE_10019 = 10019; // 解除与微信账号的绑定操作失败
        public static final int CODE_10020 = 10020; // 获取绑定信息操作失败
        public static final int CODE_10021 = 10021; // 为方便您的使用，请先注册艾特票账户！  跳转到登录注册界面
        public static final int CODE_10022 = 10022; // 保存文件时发生错误
        public static final int CODE_10023 = 10023; // 获取上传凭证失败
        public static final int CODE_10024 = 10024; // 上传文件到OSS服务器失败
        public static final int CODE_10026 = 10026; // OCR识别操作失败
        public static final int CODE_10030 = 10030; //  token失效，跳到登录界面
        public static final int CODE_10100 = 10100; // 请绑定艾特票账号
        public static final int CODE_10101 = 10101; // ocr发票已存在!
        public static final int CODE_10300 = 10300; // 未设置常用的企业名片!
        //        public static final int CODE_11001 = 11001; // 代码异常
//        public static final int CODE_10029 = 10029; //
        public static final int CODE_11010 = 11010; // 调用支付宝接口错误
        public static final int CODE_11002 = 11002; // 参数不能为空
        public static final int CODE_11004 = 11004; // 未绑定支付宝
        public static final int CODE_11005 = 11005; // 已绑定支付宝
        public static final int CODE_11006 = 11006; // 解绑成功
        //        public static final int CODE_11007 = 11007; // 绑定成功
        public static final int CODE_11008 = 11008; // 用户不存在
        public static final int CODE_12002 = 12002; //没找到该用户
        public static final int CODE_12020 = 12020; // 未设置常用的企业名片!
        public static final int CODE_50000 = 50000; // 验证码错误
        public static final int CODE_60000 = 60000; // 验证码错误

    }
}
