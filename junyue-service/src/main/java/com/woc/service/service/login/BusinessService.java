package com.woc.service.service.login;


import com.woc.common.entity.WocBusiness;

public interface BusinessService {

    public WocBusiness selectBusinessByTagAndChannelId(String businessTag, int channelId) throws Exception;
}
