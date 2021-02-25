package com.woc.service.service.login.impl;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.alibaba.fastjson.JSONObject;
import com.woc.common.common.Contants;
import com.woc.common.common.RespContants;
import com.woc.common.common.bean.RespResult;
import com.woc.common.common.config.BaseConfig;
import com.woc.common.common.exception.BusinessException;
import com.woc.common.common.utils.*;
import com.woc.common.entity.*;
import com.woc.common.vo.TUserVO;
import com.woc.dao.dao.TUserDao;
import com.woc.service.common.WeixinConfig;
import com.woc.service.service.login.*;
import com.woc.service.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private PlatformService platformService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private BusinessService businessService;

    @Autowired
    private UserService userService;

    @Autowired
    private TUserDao tUserDao;

    @Autowired
    private BaseConfig baseConfig;

    @Autowired
    private TAlarmService tAlarmService;
    @Autowired
    private WeixinConfig weixinConfig;
    @Autowired
    private RedisUtils redisUtils;


    @Value("${server.baseurl}")
    private String baseUrl;


    @Override
    public String toLogin(String platform, String channel, String business,String uuid,Integer channelId) throws Exception {

        WocPlatform wocPlatform = platformService.selectPlatformByTag(platform);

        WocChannel wocChannel = channelService.selectChannelByTagAndPlatId(channel, wocPlatform.getId());

        String indexUrl = baseUrl + "woc-web-admin/login/" + platform + "/" + channel + "/" + business + "/index";
        if(StringUtils.isNotEmpty(uuid)){
            indexUrl += "?uuid="+uuid;
            if(channelId != null){
                indexUrl += "&channelId="+channelId;
            }
        }else{
            if(channelId != null){
                indexUrl += "?channelId="+channelId;
            }
        }

        String url = wocChannel.getRedirectUrl().replace("REDIRECT_URI", URLEncoder.encode(indexUrl));


        return "redirect:" + url;

    }

    @Override
    public String toIndex(String platform, String channel, String business, String code, HttpServletRequest request,String uuid,Integer channelId) throws Exception {
        WocPlatform wocPlatform = platformService.selectPlatformByTag(platform);

        WocChannel wocChannel = channelService.selectChannelByTagAndPlatId(channel, wocPlatform.getId());

        WocBusiness wocBusiness = businessService.selectBusinessByTagAndChannelId(business, wocChannel.getId());
        wocBusiness.setRedirectUrl(wocBusiness.getRedirectUrl()+"?uuid="+uuid);

        SnsToken snsToken = SnsAPI.oauth2AccessToken(wocChannel.getAppid(), wocChannel.getAppSecret(), code);

        User user = SnsAPI.userinfo(snsToken.getAccess_token(), snsToken.getOpenid(), "zh_CN ");

        //保存用户信息
        TUser tUser = new TUser();
        tUser.setCity(user.getCity());
        tUser.setCountry(user.getCountry());
        tUser.setProvince(user.getProvince());
        tUser.setCreateTime(new Date());
        tUser.setHeadimgUrl(user.getHeadimgurl());
        tUser.setNickName(user.getNickname());
        tUser.setOpenId(snsToken.getOpenid());
        tUser.setSex(user.getSex());
        tUser.setUnionId(user.getUnionid());
        tUser.setPlatformId(wocPlatform.getId());
        tUser.setChannelId(wocChannel.getId());

        TUser newUser = userService.saveOrUpdate(tUser);


        HttpSession session = request.getSession();
        session.setAttribute(Contants.USER, newUser);
        session.setAttribute(Contants.PLATFORM, wocPlatform);
        if(channelId != null){
            wocChannel = channelService.findChannelById(channelId);
        }
        session.setAttribute(Contants.CHANNEL, wocChannel);
        session.setAttribute(Contants.BUSINESS, wocBusiness);

//        //为绑定账号
//        if(StringUtils.isEmpty(newUser.getAccount())){
//            TSchool tSchool = tSchoolService.findSchoolById(wocChannel.getSchoolId());
//
//            session.setAttribute(Contants.SCHOOL, tSchool);
////            return "redirect:" + baseConfig.getBaseUrl()+"/woc-web-admin/subLogin/index";
//            throw new AuthSchoolException("",wocBusiness,wocChannel,tUser,tSchool);
//
//        }


        return "redirect:" + wocBusiness.getRedirectUrl();


    }

//    private String subscribeIndex(WocPlatform wocPlatform,
//                                  WocChannel wocChannel,WocBusiness wocBusiness,
//                                  String code,HttpServletRequest request) throws Exception{
//
//    }

    @Override
    public RespResult toXcxLogin(String platform, String channel, String business,String code, HttpServletRequest request,String schoolTag) throws Exception{
        WocPlatform wocPlatform = platformService.selectPlatformByTag(platform);

        WocChannel wocChannel = channelService.selectChannelByTagAndPlatId(channel, wocPlatform.getId());

        WocBusiness wocBusiness = businessService.selectBusinessByTagAndChannelId(business, wocChannel.getId());

        HttpSession session = request.getSession();
        String uuid = session.getId();
        wocBusiness.setRedirectUrl(wocBusiness.getRedirectUrl()+"?uuid="+uuid);


        WxMaService wxMaService = weixinConfig.getAppMapService().get(wocChannel.getId());
        WxMaUserService userService = wxMaService.getUserService();

        WxMaJscode2SessionResult sessionInfo = userService.getSessionInfo(code);
        redisUtils.set(Contants.CODE_KEY+code,code,1,TimeUnit.MINUTES);


        if(StringUtils.isEmpty(sessionInfo.getOpenid())){
            throw new BusinessException(RespContants.CODE_ERROR);
        }

        String openid = sessionInfo.getOpenid();


        TUser tUser  = this.userService.getUserByOpenIdAndChannelId(wocChannel.getId(),openid);
        JSONObject openId = new JSONObject();
        openId.put("openId",sessionInfo.getOpenid());
        openId.put("channelId",wocChannel.getId());

        if(tUser == null){
            throw new BusinessException(RespContants.USER_NOT_LOGIN);
        }
        TUserVO tUserVO = TUserVO.getUser(tUser);
        session.setAttribute(Contants.USER, tUser);
        session.setAttribute(Contants.PLATFORM, wocPlatform);
        session.setAttribute(Contants.CHANNEL, wocChannel);
        session.setAttribute(Contants.BUSINESS, wocBusiness);

        return new RespResult(tUserVO);
    }

    @Override
    public RespResult toXcxUserLogin(String platform, String channel, String business, String encryptedData,String iv,String code,HttpServletRequest request) throws Exception {
        WocPlatform wocPlatform = platformService.selectPlatformByTag(platform);

        WocChannel wocChannel = channelService.selectChannelByTagAndPlatId(channel, wocPlatform.getId());

        WocBusiness wocBusiness = businessService.selectBusinessByTagAndChannelId(business, wocChannel.getId());


        WxMaService wxMaService = weixinConfig.getAppMapService().get(wocChannel.getId());

        WxMaUserService userService = wxMaService.getUserService();
        WxMaJscode2SessionResult sessionInfo = userService.getSessionInfo(code);
        if(StringUtils.isEmpty(sessionInfo.getOpenid())){
            throw new BusinessException(RespContants.CODE_ERROR);
        }

        WxMaUserInfo userInfo = userService.getUserInfo(sessionInfo.getSessionKey(), encryptedData, iv);


        TUser tUser = this.userService.insertXcxUser(userInfo, wocChannel);


        TUserVO tUserVO = TUserVO.getUser(tUser);

        HttpSession session = request.getSession();
        session.setAttribute(Contants.USER, tUser);
        session.setAttribute(Contants.PLATFORM, wocPlatform);
        session.setAttribute(Contants.CHANNEL, wocChannel);
        session.setAttribute(Contants.BUSINESS, wocBusiness);

        return new RespResult(tUserVO);
    }

    @Override
    public TUserVO getUserInfo(TUser tUser) {
        return TUserVO.getUser(tUser);
    }





}
