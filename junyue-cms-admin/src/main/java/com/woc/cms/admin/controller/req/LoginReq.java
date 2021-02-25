package com.woc.cms.admin.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("登录请求体")
public class LoginReq {
    @ApiModelProperty(value = "用户名")
    private String account;
    @ApiModelProperty(value = "密码")
    private String password;
}
