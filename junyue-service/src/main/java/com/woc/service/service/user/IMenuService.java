package com.woc.service.service.user;

import com.woc.common.entity.WocMenu;
import com.woc.common.entity.extend.PageParams;
import com.woc.common.entity.extend.WocMenuExtend;

import java.util.List;

public interface IMenuService {

    List<WocMenu> getPageList(PageParams page, WocMenu wocMenu);

    Boolean save(WocMenu wocMenu) throws Exception;

    Boolean update(WocMenu wocMenu);

    List<WocMenuExtend> getList();

    List<WocMenuExtend> getListByRoleId(Integer roleId);
}
