package com.woc.service.service.login.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woc.common.common.Contants;
import com.woc.common.common.RespContants;
import com.woc.common.common.bean.PRespResult;
import com.woc.common.common.exception.BusinessException;
import com.woc.common.entity.WocPlatform;
import com.woc.common.entity.WocPlatformExample;
import com.woc.dao.dao.WocPlatformDao;
import com.woc.service.service.login.PlatformService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "WOC_PLATFORM")
public class PlatformServiceImpl implements PlatformService {

    @Autowired
    private WocPlatformDao wocPlatformDao;

    @Override
    @Cacheable(key = "#p0")
    public WocPlatform selectPlatformByTag(String platformTag) throws Exception {
        WocPlatformExample wocPlatformExample = new WocPlatformExample();
        wocPlatformExample.createCriteria().andPlatformTagEqualTo(platformTag).andIsDeleteEqualTo(0);
        List<WocPlatform> wocPlatforms = wocPlatformDao.selectByExample(wocPlatformExample);

        if(wocPlatforms.isEmpty()){
            throw new BusinessException(RespContants.PLATFORM_ISNOT_EXIST);
        }
        if(wocPlatforms.size()>1){
            throw new BusinessException(RespContants.PLATFORM_EXIST_NOT_ONLY);
        }
        WocPlatform wocPlatform = wocPlatforms.get(0);
        if(wocPlatform.getStatus() != 1){
            throw new BusinessException(RespContants.PLATFORM_NOT_OPEN);
        }
        return wocPlatform;
    }

    @Override
    @Cacheable(cacheNames = "WOC_PLATFROM_PAGE" ,key="#page+'_'+#pageSize")
    public PRespResult pagelist(Integer page, Integer pageSize) {
        if(page != null && pageSize != null ){
            PageHelper.startPage(page,pageSize);
        }
        List<WocPlatform> wocPlatforms = wocPlatformDao.selectByExample(new WocPlatformExample());
        int count = wocPlatforms.size();
        if(page != null && pageSize != null ){
            count = (int)new PageInfo<>(wocPlatforms).getTotal();
        }
        return new PRespResult(wocPlatforms,count);
    }

    @Override
    public Boolean update(WocPlatform wocPlatform) {
        if(wocPlatform.getId() == null){
            new BusinessException(RespContants.PARAMS_ISNOT_RIGHT,",id未空");
        }

        if(wocPlatformDao.updateByPrimaryKeySelective(wocPlatform) >0 ){
            Session session = SecurityUtils.getSubject().getSession();
            wocPlatform = wocPlatformDao.selectByPrimaryKey(wocPlatform.getId());
            session.setAttribute(Contants.PLATFORM_TAG, wocPlatform);
        }
        return true;
    }

    @Override
    public WocPlatform findById(Integer platformId) throws Exception {
        WocPlatform wocPlatform = wocPlatformDao.selectByPrimaryKey(platformId);
        if(wocPlatform == null){
            throw new BusinessException(RespContants.PLATFORM_ISNOT_EXIST);
        }
        return wocPlatform;
    }

    @Override
    public Boolean insert(WocPlatform wocPlatform) {
        int insert = wocPlatformDao.insert(wocPlatform);
        return insert > 0;
    }
}
