package com.woc.common.vo.enumVO;

public enum  UserTypeEnum {

    /**
     * 超级管理员
     */
    ADMIN(1,1),
    /**
     * 主学校管理员
     */
    SCHOOL_ADMIN(2,1),
    /**
     * 子学校管理员
     */
    SUB_SCHOOL_ADMIN(3,1),
    /**
     * 店长
     */
    MERCHANT_ADMIN(4,1),
    /**
     * BOSS
     */
    BOSS(5,1),
    /**
     * 收款人
     */
    PAYEE_ADMIN(6,1),

    RUNNER(7,3);


    private Integer type;

    private Integer terminal;

    UserTypeEnum(Integer type, Integer terminal) {
        this.type = type;
        this.terminal = terminal;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTerminal() {
        return terminal;
    }

    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }

    public static UserTypeEnum getManagerByType(Integer type){
        UserTypeEnum[] userTypes = UserTypeEnum.values();

        for(int i = 0 ; i < userTypes.length ; i++){
            if(userTypes[i].getType().equals(type)){
                return userTypes[i];
            }
        }
        return null;

    }
}
