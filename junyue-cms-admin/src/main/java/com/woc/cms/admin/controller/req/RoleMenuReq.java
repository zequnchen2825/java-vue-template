package com.woc.cms.admin.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@ApiModel("角色权限信息")
public class RoleMenuReq {

    @ApiModelProperty(value = "角色ID", example = "111", required = true)
    private Integer roleId;
    @ApiModelProperty(value = "权限ID列表", example = "111，111", required = true)
    private String resourceIds;
}
