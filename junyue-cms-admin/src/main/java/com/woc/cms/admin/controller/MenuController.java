package com.woc.cms.admin.controller;


import com.woc.common.common.bean.RespResult;
import com.woc.common.common.utils.VerifyParamUtils;
import com.woc.common.entity.WocMenu;
import com.woc.common.entity.extend.PageParams;
import com.woc.service.service.user.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService iMenuService;

    /**
     * 分页查找菜单
     * @param page
     * @param wocMenu
     * @return
     */
    @ResponseBody
    @RequestMapping("/pageList")
    public RespResult getPageList(PageParams page, WocMenu wocMenu){

        return new RespResult(iMenuService.getPageList(page,wocMenu));

    }
    /**
     * 查找菜单
     * @return
     */
    @ResponseBody
    @RequestMapping("/getList")
    public RespResult getList() {
        return new RespResult(iMenuService.getList());
    }
    /**
     * 根据角色id查找菜单
     * @return
     */
    @ResponseBody
    @RequestMapping("/getListByRoleId")
    public RespResult getListByRoleId(Integer roleId) {
        return new RespResult(iMenuService.getListByRoleId(roleId));
    }

    @ResponseBody
    @RequestMapping("/save")
    public RespResult save(WocMenu wocMenu) throws Exception {
        return new RespResult(iMenuService.save(wocMenu));
    }

    @RequestMapping("/update")
    @ResponseBody
    public RespResult update(WocMenu wocMenu) throws Exception {
        VerifyParamUtils.verifyParam(wocMenu.getId());
        return new RespResult(iMenuService.update(wocMenu));
    }

}
