package com.woc.service.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.woc.common.common.RespContants;
import com.woc.common.common.exception.BusinessException;
import com.woc.common.common.utils.VerifyParamUtils;
import com.woc.common.entity.WocMenu;
import com.woc.common.entity.WocMenuExample;
import com.woc.common.entity.extend.PageParams;
import com.woc.common.entity.extend.WocMenuExtend;
import com.woc.dao.dao.extend.WocMenuDaoExtend;
import com.woc.service.service.user.IMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private WocMenuDaoExtend wocMenuDaoExtend;

    @Override
    public List<WocMenu> getPageList(PageParams page, WocMenu wocMenu) {

        PageHelper.startPage(page.getPage(),page.getPageSize());

        WocMenuExample wocMenuExample = new WocMenuExample();
        WocMenuExample.Criteria criteria = wocMenuExample.createCriteria();
        if(VerifyParamUtils.verifyIsNotNull(wocMenu.getMenuName())){
            criteria.andMenuNameLike(wocMenu.getMenuName());
        }
        if(VerifyParamUtils.verifyIsNotNull(wocMenu.getPermissionCode())){
            criteria.andPermissionCodeEqualTo(wocMenu.getPermissionCode());
        }
        criteria.andIsDeleteEqualTo(0);

        return wocMenuDaoExtend.selectByExample(wocMenuExample);
    }

    @Override
    public Boolean save(WocMenu wocMenu) throws Exception{
        if(wocMenu.getLevel() != 1  ){
            if(wocMenu.getpId() ==null){
                throw new BusinessException(RespContants.MENU_PID_IS_NULL);
            }

        }else{
            wocMenu.setpId(0);
        }
        if(wocMenu.getLevel() == 2 && VerifyParamUtils.verifyIsNull(wocMenu.getMenuUrl())){
            throw new BusinessException(RespContants.SERCOND_MENU_NOT_URL);
        }
        if(wocMenu.getLevel() == 3 && VerifyParamUtils.verifyIsNull(wocMenu.getPermissionCode())){
            throw new BusinessException(RespContants.THIRD_MENU_NOT_PREMISSION_CODE);
        }
        wocMenu.setCreateTime(new Date());
        wocMenu.setUpdateTime(wocMenu.getCreateTime());
        wocMenu.setStatus(1);
        wocMenu.setIsDelete(0);
        return wocMenuDaoExtend.insert(wocMenu) > 0;
    }

    @Override
    public Boolean update(WocMenu wocMenu) {
        wocMenu.setUpdateTime(new Date());
        return wocMenuDaoExtend.updateByPrimaryKeySelective(wocMenu) > 0;
    }

    @Override
    public List<WocMenuExtend> getList() {

        List<WocMenu> wocMenus = findMenuByPid(0);
        List<WocMenuExtend> wocMenuExtends = new ArrayList<>();
        //查询子菜单
        for(WocMenu wocMenu : wocMenus){
            wocMenuExtends.add(buildMenu(wocMenu));
        }

        return wocMenuExtends;
    }

    @Override
    public List<WocMenuExtend> getListByRoleId(Integer roleId) {
        return wocMenuDaoExtend.findMenuByRolesIdAndLevel(roleId.toString(),null,null);
    }

    public List<WocMenu> findMenuByPid( Integer pid){
        WocMenuExample wocMenuExample = new WocMenuExample();
        wocMenuExample.createCriteria().andIsDeleteEqualTo(0).andPIdEqualTo(pid);
        wocMenuExample.setOrderByClause("order_level");


        return wocMenuDaoExtend.selectByExample(wocMenuExample);
    }

    public WocMenuExtend buildMenu(WocMenu wocMenu) {
        WocMenuExtend wocMenuExtend = new WocMenuExtend();
        BeanUtils.copyProperties(wocMenu,wocMenuExtend);
        List<WocMenuExtend> wocMenuExtends = new ArrayList<>();
        List<WocMenu> wocMenuList = findMenuByPid(wocMenu.getId());
        for (WocMenu menu : wocMenuList) {
            wocMenuExtends.add(buildMenu(menu));
        }
        wocMenuExtend.setSubWocMenu(wocMenuExtends);
        return wocMenuExtend;
    }
}
