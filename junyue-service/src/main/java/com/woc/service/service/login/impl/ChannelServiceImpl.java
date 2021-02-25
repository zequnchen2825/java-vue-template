package com.woc.service.service.login.impl;

import com.woc.common.common.RespContants;
import com.woc.common.common.exception.BusinessException;
import com.woc.common.entity.WocChannel;
import com.woc.common.entity.WocChannelExample;
import com.woc.dao.dao.WocChannelDao;
import com.woc.service.service.login.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "WOC_CHANNEL")
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private WocChannelDao wocChannelDao;

    @Override
    @Cacheable(key="#channelTag+'_'+#platformId")
    public WocChannel selectChannelByTagAndPlatId(String channelTag, Integer platformId) throws Exception {
        WocChannelExample wocChannelExample = new WocChannelExample();
        wocChannelExample.createCriteria().andChannelTagEqualTo(channelTag).andPlatformIdEqualTo(platformId).andIsDeleteEqualTo(0);
        List<WocChannel> wocChannels = wocChannelDao.selectByExample(wocChannelExample);
        if(wocChannels.isEmpty()){
            throw new BusinessException(RespContants.CHANNEL_ISNOT_EXIST);
        }
        if(wocChannels.size()>1){
            throw new BusinessException(RespContants.CHANNEL_EXIST_NOT_ONLY);
        }
        if(wocChannels.get(0).getStatus() != 1){
            throw new BusinessException(RespContants.CHANNEL_NOT_OPEN);
        }
        return wocChannels.get(0);
    }

    @Override
    public List<WocChannel> findAllChannel() {
        return wocChannelDao.selectByExample(new WocChannelExample());
    }

    @Override
    @Cacheable(key="#channelId")
    public WocChannel findChannelById(Integer channelId) throws Exception {
        WocChannel wocChannel = wocChannelDao.selectByPrimaryKey(channelId);
        if(wocChannel == null){
            throw new BusinessException(RespContants.CHANNEL_ISNOT_EXIST);
        }
        if(wocChannel.getStatus() != 1){
            throw new BusinessException(RespContants.CHANNEL_NOT_OPEN);
        }
        return wocChannel;
    }

    @Override
    public List<WocChannel> findAllChannelByPlatformId(Integer platfromId) throws Exception {
        WocChannelExample wocChannelExample = new WocChannelExample();
        wocChannelExample.createCriteria().andPlatformIdEqualTo(platfromId).andIsDeleteEqualTo(0);
        return wocChannelDao.selectByExample(wocChannelExample);
    }

    @Override
    @Cacheable(key = "#schoolId+'_'+#platformId")
    public WocChannel findChannelBySchoolAndPlatformId(Integer schoolId, Integer platformId) throws Exception {
        WocChannelExample wocChannelExample = new WocChannelExample();
        wocChannelExample.createCriteria().andSchoolIdEqualTo(schoolId).andPlatformIdEqualTo(platformId).andIsDeleteEqualTo(0);
        List<WocChannel> wocChannels = wocChannelDao.selectByExample(wocChannelExample);
        if(wocChannels.isEmpty()){
            throw new BusinessException(RespContants.CHANNEL_ISNOT_EXIST);
        }
        if(wocChannels.size()>1){
            throw new BusinessException(RespContants.CHANNEL_EXIST_NOT_ONLY);
        }
        if(wocChannels.get(0).getStatus() != 1){
            throw new BusinessException(RespContants.CHANNEL_NOT_OPEN);
        }
        return wocChannels.get(0);
//        return null;
    }

    @Override
    public Boolean insert(WocChannel wocChannel) {
        return wocChannelDao.insert(wocChannel) > 0;
    }

    @Override
    public Boolean update(WocChannel wocChannel) {
        return wocChannelDao.updateByPrimaryKey(wocChannel) > 0;
    }
}
