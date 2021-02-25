package com.woc.service.service.user;

import com.woc.common.common.bean.PRespResult;
import com.woc.common.common.bean.RespResult;
import com.woc.common.entity.WocRole;
import com.woc.common.entity.extend.PageParams;

import java.util.List;

public interface IRoleService {
    PRespResult findRolePageList(PageParams pageParams, WocRole wocRole);

    WocRole saveRole(WocRole wocRole) throws Exception;

    Boolean updateRole(WocRole wocRole);

    Boolean updateRoleMenu(List<Integer> addList, List<Integer> removeList, Integer roleId);

    RespResult findByResource(Integer resourceId, Integer page, Integer limit);
}
