package com.woc.cms.admin.controller;


import com.woc.common.common.bean.PRespResult;
import com.woc.common.common.bean.RespResult;
import com.woc.common.common.utils.VerifyParamUtils;
import com.woc.common.entity.WocRole;
import com.woc.common.entity.WocRoleMenu;
import com.woc.common.entity.WocRoleMenuExample;
import com.woc.common.entity.extend.PageParams;
import com.woc.dao.dao.WocRoleMenuDao;
import com.woc.service.service.user.IRoleService;
import com.woc.cms.admin.controller.req.RoleMenuReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 *  @author: chenzequn
 *  @Date: 2019/8/5 18:45
 *  @Description: 角色控制器
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private WocRoleMenuDao wocRoleMenuDao;

    /**
     * 获取角色列表
     * @param pageParams
     * @param wocRole
     * @return
     */
    @ResponseBody
    @RequestMapping("/pageList")
    public PRespResult findRolePageList(PageParams pageParams , WocRole wocRole){
        return iRoleService.findRolePageList(pageParams,wocRole);
    }

    /**
     * 新增角色
     * @param wocRole
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/save")
    public RespResult saveRole(WocRole wocRole) throws Exception {
        VerifyParamUtils.verifyParam(wocRole.getRoleName(),wocRole.getRoleCode(),wocRole.getSort());

        return new RespResult(iRoleService.saveRole(wocRole));

    }
    /**
     * 编辑或删除角色
     * @param wocRole
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/update")
    public RespResult updateRole(WocRole wocRole) throws Exception{
        VerifyParamUtils.verifyParam(wocRole.getId());
        return new RespResult(iRoleService.updateRole(wocRole));
    }

    /**
     * 修改角色菜单
     * @param addList
     * @param removeList
     * @param userId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/updateRoleMenu")
    public RespResult updateRoleMenu(List<Integer> addList, List<Integer> removeList, Integer RoleId) throws Exception {
        VerifyParamUtils.verifyParam(RoleId);
        return new RespResult(iRoleService.updateRoleMenu(addList,removeList,RoleId));

    }

    /**
     * 根据资源id查找角色
     * @param resourceId
     * @return
     */
    @ResponseBody
    @RequestMapping("/findByResource")
    public RespResult findByResource(Integer resourceId,Integer page, Integer limit){
        VerifyParamUtils.verifyParam(resourceId);
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 20;
        }
        return iRoleService.findByResource(resourceId, page, limit);
    }

    @ResponseBody
    @RequestMapping("/menu/add")
    public RespResult menuAdd(@RequestBody RoleMenuReq roleMenuReq) throws Exception {
        WocRoleMenu wocRoleMenu = new WocRoleMenu();
        wocRoleMenu.setIsDelete(1);
        WocRoleMenuExample wocRoleMenuExample = new WocRoleMenuExample();
        wocRoleMenuExample.createCriteria().andRoleIdEqualTo(roleMenuReq.getRoleId());
        wocRoleMenuDao.updateByExampleSelective(wocRoleMenu, wocRoleMenuExample);
        // 添加新权限
        if (!StringUtils.isEmpty(roleMenuReq.getResourceIds())) {
            String[] ids = roleMenuReq.getResourceIds().split(",");
            if (ids != null) {
                wocRoleMenu.setRoleId(roleMenuReq.getRoleId());
                wocRoleMenu.setIsDelete(0);
                wocRoleMenu.setCreateTime(new Date());
                for (String id : ids) {
                    wocRoleMenu.setMenuId(Integer.valueOf(id));
                    wocRoleMenu.setId(null);
                    wocRoleMenuDao.insert(wocRoleMenu);
                }
            }
        }
        return new RespResult(true);
    }

}
