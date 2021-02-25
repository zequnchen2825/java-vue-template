package com.woc.service.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woc.common.common.RespContants;
import com.woc.common.common.bean.PRespResult;
import com.woc.common.common.bean.RespResult;
import com.woc.common.common.exception.BusinessException;
import com.woc.common.common.utils.VerifyParamUtils;
import com.woc.common.entity.WocRole;
import com.woc.common.entity.WocRoleExample;
import com.woc.common.entity.WocRoleMenu;
import com.woc.common.entity.WocRoleMenuExample;
import com.woc.common.entity.extend.PageParams;
import com.woc.dao.dao.WocRoleDao;
import com.woc.dao.dao.WocRoleMenuDao;
import com.woc.service.service.user.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private WocRoleDao wocRoleDao;

    @Autowired
    private WocRoleMenuDao wocRoleMenuDao;

    @Override
    public PRespResult findRolePageList(PageParams pageParams, WocRole wocRole) {
        if(pageParams.getPage() !=null && pageParams.getPageSize() != null){
            PageHelper.startPage(pageParams.getPage(),pageParams.getPageSize());
        }
        WocRoleExample wocRoleExample = new WocRoleExample();
        WocRoleExample.Criteria criteria = wocRoleExample.createCriteria();
        if(VerifyParamUtils.verifyIsNotNull(wocRole.getId())){
            criteria.andIdEqualTo(wocRole.getId());
        }
        if(VerifyParamUtils.verifyIsNotNull(wocRole.getRoleCode())){
            criteria.andRoleCodeEqualTo(wocRole.getRoleCode());
        }
        if(VerifyParamUtils.verifyIsNotNull(wocRole.getRoleName())){
            criteria.andRoleNameLike(wocRole.getRoleName());
        }

        criteria.andIsDeleteEqualTo(0);
        wocRoleExample.setOrderByClause("sort asc");
        List<WocRole> wocRoles = wocRoleDao.selectByExample(wocRoleExample);
        int count = wocRoles.size();
        if(pageParams.getPage() !=null && pageParams.getPageSize() != null){
            count = (int)new PageInfo<>(wocRoles).getTotal();
        }
        return new PRespResult(wocRoles,count);
    }

    @Override
    public WocRole saveRole(WocRole wocRole) throws Exception{
        WocRoleExample wocRoleExample = new WocRoleExample();
        wocRoleExample.createCriteria().andRoleCodeEqualTo(wocRole.getRoleCode()).andIsDeleteEqualTo(0);
        List<WocRole> wocRoles = wocRoleDao.selectByExample(wocRoleExample);
        if(!wocRoles.isEmpty()){
            throw new BusinessException(RespContants.ROLE_CODE_IS_EXIST);
        }

        wocRoleExample = new WocRoleExample();
        wocRoleExample.createCriteria().andRoleNameEqualTo(wocRole.getRoleName()).andIsDeleteEqualTo(0);
        wocRoles = wocRoleDao.selectByExample(wocRoleExample);
        if(!wocRoles.isEmpty()){
            throw new BusinessException(RespContants.ROLE_CODE_IS_EXIST);
        }
        wocRole.setCreatetime(new Date());
        wocRole.setIsDelete(0);
        if (wocRoleDao.insert(wocRole) > 0) {
            return wocRole;
        }
        throw new  BusinessException(RespContants.ADD_FAILE);
    }

    @Override
    public Boolean updateRole(WocRole wocRole) {

        return wocRoleDao.updateByPrimaryKey(wocRole) > 0;
    }

    @Override
    public Boolean updateRoleMenu(List<Integer> addList, List<Integer> removeList, Integer roleId) {

        for(Integer menuId : addList){
            WocRoleMenu wocRoleMenu = new WocRoleMenu();

            wocRoleMenu.setCreateTime(new Date());
            wocRoleMenu.setRoleId(roleId);
            wocRoleMenu.setIsDelete(0);
            wocRoleMenu.setMenuId(menuId);
            wocRoleMenuDao.insert(wocRoleMenu);
        }
        for(Integer menuId : removeList){

            WocRoleMenuExample wocRoleMenuExample = new WocRoleMenuExample();
            wocRoleMenuExample.createCriteria().andMenuIdEqualTo(menuId).andRoleIdEqualTo(roleId);
            WocRoleMenu wocRoleMenu = new WocRoleMenu();
            wocRoleMenu.setIsDelete(1);
            wocRoleMenuDao.updateByExample(wocRoleMenu,wocRoleMenuExample);


        }
        return true;
    }

    @Override
    public RespResult findByResource(Integer resourceId, Integer page, Integer limit) {
        WocRoleMenuExample wocRoleMenuExample = new WocRoleMenuExample();
        wocRoleMenuExample.createCriteria().andIsDeleteEqualTo(0).andMenuIdEqualTo(resourceId);
        long count = wocRoleMenuDao.countByExample(wocRoleMenuExample);
        wocRoleMenuExample.setLimit(limit);
        wocRoleMenuExample.setOffset((long) (page-1) * limit);
        List<WocRoleMenu> wocRoleMenus = wocRoleMenuDao.selectByExample(wocRoleMenuExample);
        List<WocRole> wocRoles = new ArrayList<>();
        for (WocRoleMenu wocRoleMenu : wocRoleMenus) {
            wocRoles.add(wocRoleDao.selectByPrimaryKey(wocRoleMenu.getRoleId()));
        }
        RespResult respResult = new RespResult(wocRoles);
        respResult.setCount(count);
        return respResult;
    }
}
