package com.woc.service.service.login;

import com.woc.common.entity.WocChannel;

import java.util.List;

public interface ChannelService {

    public WocChannel selectChannelByTagAndPlatId(String channelTag, Integer platformId) throws Exception;

    List<WocChannel> findAllChannel();

    WocChannel findChannelById(Integer channelId) throws Exception;

    List<WocChannel> findAllChannelByPlatformId(Integer platfromId) throws Exception;

    WocChannel findChannelBySchoolAndPlatformId(Integer id, Integer id1) throws Exception;

    Boolean insert(WocChannel wocChannel);

    Boolean update(WocChannel wocChannel);
}
