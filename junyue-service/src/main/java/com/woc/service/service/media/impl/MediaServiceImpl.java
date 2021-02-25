package com.woc.service.service.media.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woc.common.common.RespContants;
import com.woc.common.common.bean.PRespResult;
import com.woc.common.common.exception.BusinessException;
import com.woc.common.common.shiro.ShiroManager;
import com.woc.common.common.utils.VerifyParamUtils;
import com.woc.common.entity.*;
import com.woc.common.entity.extend.DO.MediaGroupExtendDO;
import com.woc.common.entity.extend.DO.WocMediaExtendDO;
import com.woc.common.entity.extend.MediaGroupExtend;
import com.woc.common.entity.extend.PageParams;
import com.woc.common.entity.extend.WocMediaExtend;
import com.woc.dao.dao.extend.MediaGroupDaoExtend;
import com.woc.dao.dao.extend.WocMediaDaoExtend;
import com.woc.service.service.media.MediaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private MediaGroupDaoExtend mediaGroupDaoExtend;
    @Autowired
    private WocMediaDaoExtend wocMediaDaoExtend;

    @Override
    public PRespResult groupPageList(PageParams pageParams, MediaGroupExtendDO mediaGroup) {
        if(pageParams.getPageSize() != null && pageParams.getPage() != null){
            PageHelper.startPage(pageParams.getPage(),pageParams.getPageSize());
        }
        WocPlatform platform = ShiroManager.getPlatform();
        mediaGroup.setPlatfromId(platform.getId());
        List<MediaGroupExtend> mediaGroups = mediaGroupDaoExtend.findMediaGroup(mediaGroup);
        return new PRespResult(mediaGroups,(int)new PageInfo<>(mediaGroups).getTotal());
    }

    @Override
    public PRespResult pageList(PageParams pageParams, WocMediaExtendDO wocMedia) {
        if(pageParams !=null ){
            PageHelper.startPage(pageParams.getPage(),pageParams.getPageSize());
        }


        if(StringUtils.isEmpty(wocMedia.getOrder())){
            wocMedia.setOrder("create_time");
        }
        WocPlatform platform = ShiroManager.getPlatform();
        wocMedia.setPlatformId(platform.getId());
        List<WocMediaExtend> wocMedias = wocMediaDaoExtend.findMedia(wocMedia);
        return new PRespResult(wocMedias,(int)new PageInfo<>(wocMedias).getTotal());
    }

    @Override
    public Boolean saveGroup(MediaGroup mediaGroup) throws Exception {

        if(mediaGroup.getId() != null){
            return updataGroup(mediaGroup);
        }
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("channelId",mediaGroup.getChannelId());
        paramMap.put("channelName",mediaGroup.getGroupName());
        paramMap.put("groupTag",mediaGroup.getGroupTag());
        paramMap.put("height",mediaGroup.getHeight());
        paramMap.put("width",mediaGroup.getWidth());
        VerifyParamUtils.verifyParam(paramMap);
        MediaGroupExample mediaGroupExample = new MediaGroupExample();
        mediaGroupExample.createCriteria().andGroupTagEqualTo(mediaGroup.getGroupTag())
                .andChannelIdEqualTo(mediaGroup.getChannelId())
                .andIsDeleteEqualTo(0);
        List<MediaGroup> mediaGroups = mediaGroupDaoExtend.selectByExample(mediaGroupExample);
        if(!mediaGroups.isEmpty()){
            throw new BusinessException(RespContants.MEDIA_TAG_EXIST);
        }
        mediaGroupExample = new MediaGroupExample();
        mediaGroupExample.createCriteria().andGroupNameEqualTo(mediaGroup.getGroupName())
                .andChannelIdEqualTo(mediaGroup.getChannelId())
                .andIsDeleteEqualTo(0);
        mediaGroups = mediaGroupDaoExtend.selectByExample(mediaGroupExample);
        if(!mediaGroups.isEmpty()){
            throw new BusinessException(RespContants.MEDIA_GROUP_NAME_EXIST);
        }
        mediaGroup.setCreateTime(new Date());
        mediaGroup.setIsDelete(0);

        return mediaGroupDaoExtend.insert(mediaGroup) >0;
    }

    @Override
    public Boolean updataGroup(MediaGroup mediaGroup) throws Exception {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",mediaGroup.getId());
        VerifyParamUtils.verifyParam(paramMap);
        return mediaGroupDaoExtend.updateByPrimaryKeySelective(mediaGroup)>0;
    }

    @Override
    public MediaGroup findGroupById(Integer id) throws Exception{
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        VerifyParamUtils.verifyParam(paramMap);
        return mediaGroupDaoExtend.selectByPrimaryKey(id);
    }

    @Override
    public Boolean save(WocMedia media) throws Exception {

        if(media.getId() != null){
            return updata(media);
        }
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("groupId",media.getGroupId());
        paramMap.put("mediaUrl",media.getMediaUrl());
        paramMap.put("orderBy",media.getOrderBy());
        paramMap.put("thumbnailImgUrl",media.getThumbnailImgUrl());
        paramMap.put("title",media.getTitle());
        paramMap.put("type",media.getType());
        VerifyParamUtils.verifyParam(paramMap);

        WocPlatform platform = ShiroManager.getPlatform();

        List<WocMedia> wocMedias = wocMediaDaoExtend.selectSelfByTitle(media.getTitle(),platform.getId());
        if(!wocMedias.isEmpty()){
            throw new BusinessException(RespContants.MEDIA_NAME_EXIST);
        }

        media.setCreateTime(new Date());
        media.setUpdateTime(media.getCreateTime());
        media.setIsDelete(0);

        return wocMediaDaoExtend.insert(media) >0;
    }

    @Override
    public WocMedia findById(Integer id) throws Exception {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",id);
        VerifyParamUtils.verifyParam(paramMap);
        return wocMediaDaoExtend.selectByPrimaryKey(id);
    }

    @Override
    public Boolean updata(WocMedia media) throws Exception {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("id",media.getId());
        VerifyParamUtils.verifyParam(paramMap);

        if(StringUtils.isNotEmpty(media.getTitle())){
            WocMedia wocMedia = wocMediaDaoExtend.selectByPrimaryKey(media.getId());

            if(!wocMedia.getTitle().equals(media.getTitle())){
                WocPlatform platform = ShiroManager.getPlatform();

                List<WocMedia> wocMedias = wocMediaDaoExtend.selectSelfByTitle(media.getTitle(),platform.getId());
                if(!wocMedias.isEmpty()){
                    throw new BusinessException(RespContants.MEDIA_NAME_EXIST);
                }
            }

        }
        return wocMediaDaoExtend.updateByPrimaryKeySelective(media)>0;
    }

    @Override
    public MediaGroupExtend getMedia(String platformTag, String groupTag,String channelTag) {
        MediaGroupExtend mediaGroupExtend = mediaGroupDaoExtend.getMediaGroupByPCG(platformTag,channelTag,groupTag);
        if(mediaGroupExtend != null){
            WocMediaExtendDO wocMediaExtendDO = new WocMediaExtendDO();
            wocMediaExtendDO.setOrder("order_by");
            wocMediaExtendDO.setGroupId(mediaGroupExtend.getId());
            wocMediaExtendDO.setPlatformId(mediaGroupExtend.getPlatformId());
            List<WocMediaExtend> wocMedias = wocMediaDaoExtend.findMedia(wocMediaExtendDO);
            mediaGroupExtend.setWocMediaExtends(wocMedias);

            return mediaGroupExtend;
        }
        return null;
    }
}
