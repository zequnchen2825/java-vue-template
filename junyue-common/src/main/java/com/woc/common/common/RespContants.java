package com.woc.common.common;


public enum RespContants {


    //800-900 登录状态
    PASSWORD_ERROR(800,"账号或密码有误"),
    LOGIN_ERROR(801,"登录失败"),
    AUTO_LOGIN_ERROR(803,"自动登录失败"),
    YZM_ERROR(802,"验证码错误"),
    USER_NOT_LOGIN(809,"用户未登录过"),
    USERINFO_ERROR(10013,"用户信息丢失,请重新进入页面"),

    //901-1000 系统状态
    PARAMS_ISNOT_RIGHT(900,"参数不正确"),
    NET_ERRER(999,"网络数据延迟"),
    YZM_IS_NULL(966,"请输入验证码"),
    //1001-1100 账户状态
    PLATFORM_ISNOT_EXIST(1000,"平台不存在"),
    PLATFORM_EXIST_NOT_ONLY(1002,"tag存在多个平台"),
    PLATFORM_NOT_OPEN(1001,"平台未开启"),
    PLATFORM_TAG_IS_NULL(1003,"平台tag未空"),
    SERCOND_MENU_NOT_URL(1004,"二级菜单需要传menuUrl"),
    MENU_PID_IS_NULL(1005,"子菜单及权限需要传PID"),
    THIRD_MENU_NOT_PREMISSION_CODE(1005,"权限需要传premissionCode"),
    CHANNEL_ISNOT_EXIST(1006,"渠道不存在"),
    CHANNEL_EXIST_NOT_ONLY(1007,"tag存在多个渠道"),
    CHANNEL_NOT_OPEN(1008,"渠道未开启"),
    BUSINESS_ISNOT_EXIST(1009,"业务不存在"),
    BUSINESS_EXIST_NOT_ONLY(1010,"tag存在多个业务"),
    BUSINESS_NOT_OPEN(1011,"业务未开启"),

    ADDRESS_IS_TOO_MUCH(1012,"收件地址最多保存5个"),
    //1101-1200 账户状态
    USER_ISNOT_EXIST(1101,"用户名不存在"),
    USER_LENGTH_ERRER(1102,"用户名长度需要在7到15之间"),
    USER_PASSWORD_LENGTH_ERRER(1103,"密码长度需要在7到15之间"),
    USER_START_ISNOT_ALPHABET(1104,"用户名必须为字母开头"),
    USER_ILLAGE(1015,"非法用户名"),
    USER_EXIST_NOT_ONLY(1016,"用户名存在多个账户"),
    USER_PASSWORD_ERROR(1017,"账号密码错误"),
    USER_PASSWORD_IS_NULL(1018,"密码为空"),
    USER_ACCOUNT_IS_NULL(1019,"账号为空"),
    USER_ISNOT_ADMIN(1020,"用户非管理员"),
    USER_IS_EXIST(1021,"用户名已存在"),
    ROLE_CODE_IS_EXIST(1022,"角色code已存在"),
    ROLE_NAME_IS_EXIST(1023,"角色名已存在"),
    ROLE_NAME_IS_NOT_EXIST(1027,"角色不存在"),

    SCHEDLE_UPDATED(1024,"今天已经更新过"),

    GRADE_UPDATED(1025,"稍后再更新:"),
    STUDENGT_ISNOT_EXIST(1026,"学生不存在"),

    NOT_PREMISSION(1027,"没有此权限"),
    BIND_FAILE(1028,"绑定失败"),

    SUPER_MANAGER_EXIST(1029,"超级管理员被绑定"),
    MARCHANT_NOT_EXIST(1030,"商户不存在"),
    CANTEEN_NOT_EXIST(1031,"饭堂不存在"),
    REMOVE_BIND_FAILE(1032,"解除绑定失败"),
    ACTION_TIME_ERROR(1033,"时间有冲突"),
    BOSS_HAS_MARCHANT(1034,"该boss拥有商户，无法删除，需要更改boss解绑后重新绑定即可"),
    CANTEEN_HAS_MARCHANT(1035,"该饭堂拥有商户，无法删除"),
    SORT_BEYOND_COUNT(1036,"主分类数量超出设定"),
    SORT_NOT_EXIST(1037,"主分类不存在"),
    ADD_FAILE(1038,"添加失败"),
    MIN_LAGER_MAX(1040,"最小选择项大于最大选择项"),
    AT_LEAST_ONE(1041,"至少存在一个规格"),
    EXIST_SCHOOL_ADMIN(1042,"该校园存在校园管理员，无法生成绑定code"),
    IS_SCHOOL_ADMIN(1043,"你已是该学校校园管理员"),
    EXCHANG_ERROR(1044,"交换排序不属于同属"),
    CANTEEN_NAME_EXIST(1045,"饭堂已存在"),
    SORT_NAME_EXIST(1046,"分类已存在"),
    MERCHANT_NAME_EXIST(1046,"商店已存在"),
    MERCHANT_MENU_SORT_NOT_EXIST(1047,"菜单分类不存在"),
    MERCHANT_MENU_SORT_EXIST(1047,"菜单分类已存在"),
    SELECT_LESS_THAN_MIN(1048,"选择类目小于最小选择"),
    SELECT_MORE_THAN_MAN(1048,"选择类目大于最大选择"),
    MENU_IS_EXIST(1049,"菜单已存在"),
    MENU_IS_NOT_EXIST(1050,"菜单不存在"),
    SELECT_NOT_EXIST(1051,"子菜单不存在"),
    FINDSPECIFICATION_NOT_EXIST(1052,"规格不存在"),
    CODE_ERROR(1053,"code被用过或过期"),
    BEFORE_LOGIN(1054,"请先登录"),
    RECOMMED_NOT_EXIST(1055,"推荐不存在"),
    RECOMMED_EXIST(1057,"商店推荐已存在"),
    CANTEEN_NOT_ONLY_ONE(1056,"单个饭堂不需要设置推荐"),
    DORMITROY_EXIST(1057,"宿舍楼名已存在"),
    DORMITROY_NOT_EXIST(1057,"宿舍楼不存在"),
    USERTYPE_NOT_EXIST(1058,"用户身份不存在"),
    PAYEE_EXIST(1059,"收款者已存在"),
    ACTIONTIME_NOT_EXIST(1059,"时间配置不存在"),
    PLATFORM_FEE_ERROR(1060,"平台抽点有误"),
    SCHOOL_SPECIAL_ACTION_ERROR(1061,"学校专送时间有误"),
    ACTION_ERROR(1062,"生效时间不允许在开始时间与结束时间之间"),
    ACTION_EXIST(1063,"时间配置不存在"),
    MENU_SELECT_ERROR(1064,"缺失必要子菜单"),
    SELECT_MENU_ERROR(1065,"子菜单选择不存在"),
    MENU_IS_NOT_SAME(1066,"菜单不属于同个商户"),
    CREATE_WEICHAT_ORDER_ERROR(1067,"下单到微信错误"),
    ORDER_NOT_EXIST(1068,"订单不存在"),
    ORDER_IS_DEAL(1086,"订单正在处理中,请刷新再试"),
    ORDER_ISNOT_SPECIAL(1087,"订单非专送单"),
    ORDER_HAS_RUNNER(1088,"订单已有跑腿接单"),
    NOT_SUPPORT(1069,"当前渠道不支持"),
    SIZE_IS_TOO_LAGER(1070,"文件超出限制大小"),
    FILE_TYPE_ERROE(1071,"文件类型有误"),
    TERMINAL_ERROE(1072,"请到对应端绑定"),
    BANNER_COUNT_MAX(1073,"横幅数量不能超过5个"),
    BANNER_NOT_EXIST(1074,"横幅不存在"),
    ORDER_NOT_YOUR(1075,"不属于你的订单"),
    ORDER_NOT_PAY(1076,"订单未支付"),
    ORDER_IS_FINISH(1077,"订单已超出可退款时间"),
    ORDER_NOT_APPLY(1078,"订单未申请退款"),
    REFUNDY_FAIL(1079,"退款失败"),
    AMOUNT_ERROR(1080,"金额错误"),
    AMOUNT_NOT_ENOUNG(1081,"余额不足"),
    QUOTA_NOT_ENOUNG(1082,"提现额度不足"),
    WITHDRAW_FAIL(1083,"提现失败"),
    PRINTER_BAND_ERROR(1084,"打印机品牌有误"),
    PRINTER_ERROR(1085,"打印机机器码或口令"),
    RUNNER_IS_EXIST(1088,"已绑定跑腿"),
    VERIFYCODE_IS_NOT_EXIST(1089,"送达码有误"),
    SPECAL_ORDER_IS_ORTHER(1090,"专送单已被其他人接单"),
    SNAPUP_IS_NOT_EXIST(1091,"秒杀设置不存在"),
    ORDER_IS_SEND(1092,"订单已送达过"),



    //学校状态 1201- 1300
    SCHOOL_IS_NOT_EXIST(1201,"学校不存在"),
    SCHOOL_IS_NOT_CONFIG(1202,"学校不存在配置"),
    SCHOOL_IS_NOT_OPEN(1203,"学校没被开启"),
    SCHOOL_EXIST_NOT_ONLY(1204,"tag存在多个学校"),
    COURSE_IS_NOT_EXIST(1205,"课程不存在"),
    ACCOUNT_IS_NOT_EXIST(1206,"学号已被绑定"),
    SCHOOL_TIME_IS_NOT_EXIST(1207,"学校开学时间未设定"),

    //参数状态 1301 - 1400
    TERM_ERROR(1301,"学期不正确"),
    SCHOOL_TIME_ERROR(1302,"开学时间不存在"),
    TYPE_ERROR(1303,"type类型错误"),
    COURSE_NO_ERROR(1304,"课程编号错误"),
    TEMPLATE_ID_ERROR(1305,"模板不存在"),
    TEMPLATE_CHANNEL_ERROR(1306,"模板在渠道下不存在"),
    TEMPLATE_PARAM_ERROR(1306,"模板参数错误"),
    ACCESSTOKEN_ERROR(1307,"accessToken不是最新"),
    MEDIA_TAG_EXIST(1308,"tag有存在的分组"),
    MEDIA_GROUP_NAME_EXIST(1309,"分组名有存在的分组"),
    MEDIA_NAME_EXIST(1310,"媒体名已经存在"),
    CODE_ISNOT_EXIST(1311,"code不存在"),
    CODE_EXIST(1313,"已经生成code"),
    BIND_CODE_EXIST(1314,"绑定code不存在"),



    //运行遇到的错误 1401 - 1600
    SERVICE_ERROR(1401,"网络数据延迟，请稍后再试"),
    ;



    private int code;
    private String msg;
    private Object data;
    RespContants(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    RespContants(int code, String msg,Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
