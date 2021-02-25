package com.woc.service.service.login.impl;


import com.woc.common.common.RespContants;
import com.woc.common.common.exception.BusinessException;
import com.woc.common.entity.WocBusiness;
import com.woc.common.entity.WocBusinessExample;
import com.woc.dao.dao.WocBusinessDao;
import com.woc.service.service.login.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "WOC_BUSINESS")
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private WocBusinessDao wocBusinessDao;

    @Override
    @Cacheable(key = "#businessTag+'_'+#channelId")
    public WocBusiness selectBusinessByTagAndChannelId(String businessTag, int channelId) throws Exception {
        WocBusinessExample wocBusinessExample = new WocBusinessExample();
        wocBusinessExample.createCriteria().andBusinessTagEqualTo(businessTag).andChannelIdEqualTo(channelId).andIsDeleteEqualTo(0);

        List<WocBusiness> wocBusinesses = wocBusinessDao.selectByExample(wocBusinessExample);
        if(wocBusinesses.isEmpty()){
            throw new BusinessException(RespContants.BUSINESS_ISNOT_EXIST);
        }
        if(wocBusinesses.size()>1){
            throw new BusinessException(RespContants.BUSINESS_EXIST_NOT_ONLY);
        }
        if(wocBusinesses.get(0).getStatus() != 1){
            throw new BusinessException(RespContants.BUSINESS_NOT_OPEN);
        }
        return wocBusinesses.get(0);
    }
}
