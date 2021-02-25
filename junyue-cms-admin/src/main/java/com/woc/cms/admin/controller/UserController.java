package com.woc.cms.admin.controller;

import com.woc.common.common.bean.RespResult;
import com.woc.common.common.exception.BusinessException;
import com.woc.common.common.utils.VerifyParamUtils;
import com.woc.common.entity.WocUser;
import com.woc.service.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  @author: chenzequn
 *  @Date: 2019/8/4 11:24
 *  @Description: 用户控制类
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService iUserService;

    /**
     * 获取用户列表
     * @param page
     * @param pageSize
     * @param platformTag
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/pageList")
    public RespResult getUserList(@RequestParam( defaultValue = "1") Integer page,
                                  @RequestParam( defaultValue = "10")Integer pageSize,
                                  String platformTag,
                                  String account) throws Exception{
        return new RespResult(iUserService.getUserList(page,pageSize,platformTag,account));
    }

    /**
     * 新增用户
     * @param wocUser
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/save")
    public RespResult saveUser(WocUser wocUser,Integer roleId) throws Exception{
        Map<String,Object> params = new HashMap<>();
        params.put("password",wocUser.getPassword());
        params.put("account",wocUser.getAccount());
        params.put("platformId",wocUser.getPlatformId());
        params.put("roleId",roleId);
       VerifyParamUtils.verifyParam(params);

       VerifyParamUtils.verifyAccount(wocUser.getAccount());


       VerifyParamUtils.verifyPassword(wocUser.getPassword());

       return new RespResult(iUserService.saveUser(wocUser,roleId));

    }

    /**
     * 编辑删除
     * @param wocUser
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/update")
    public RespResult updateUser(WocUser wocUser) throws Exception{
        VerifyParamUtils.verifyParam(wocUser.getId());
        return new RespResult(iUserService.updateUser(wocUser));
    }

    /**
     * 修改密码接口
     * @param wocUser
     * @param newPwd
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/updatePwd")
    public RespResult updatePassword(WocUser wocUser,String newPwd) throws Exception{
        VerifyParamUtils.verifyParam(wocUser.getAccount(),wocUser.getPassword(),newPwd);
        VerifyParamUtils.verifyPassword(wocUser.getPassword());
        try {
            VerifyParamUtils.verifyPassword(newPwd);
        } catch (BusinessException e) {
           e.setMsg("新"+e.getMsg());
           throw e;
        }
        return new RespResult(iUserService.updateUserPwd(wocUser,newPwd));
    }

    /**
     * 获取用户分配的所有角色
     * @param userId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/getUserRoleList")
    public RespResult getUserRoleListByUserId(Integer userId) throws Exception{
        VerifyParamUtils.verifyParam(userId);
        return new RespResult(iUserService.getUserRoleListByUserId(userId));
    }

    /**
     * 修改用户角色
     * @param addList
     * @param removeList
     * @param userId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/updateUserRole")
    public RespResult updateUserRole(List<Integer> addList, List<Integer> removeList,Integer userId) throws Exception {
        VerifyParamUtils.verifyParam(userId);
        return new RespResult(iUserService.updateUserRole(addList,removeList,userId));

    }
}
