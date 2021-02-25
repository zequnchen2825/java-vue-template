package com.woc.service.service.media;

import com.woc.common.common.bean.PRespResult;
import com.woc.common.common.exception.BusinessException;
import com.woc.common.entity.MediaGroup;
import com.woc.common.entity.WocMedia;
import com.woc.common.entity.extend.DO.MediaGroupExtendDO;
import com.woc.common.entity.extend.DO.WocMediaExtendDO;
import com.woc.common.entity.extend.MediaGroupExtend;
import com.woc.common.entity.extend.PageParams;

public interface MediaService {
    PRespResult groupPageList(PageParams pageParams, MediaGroupExtendDO mediaGroup);

    PRespResult pageList(PageParams pageParams, WocMediaExtendDO wocMedia);

    Boolean saveGroup(MediaGroup mediaGroup) throws BusinessException, Exception;

    Boolean updataGroup(MediaGroup mediaGroup) throws Exception;

    MediaGroup findGroupById(Integer id) throws Exception;

    Boolean save(WocMedia media) throws Exception;

    WocMedia findById(Integer id) throws Exception;

    Boolean updata(WocMedia media) throws Exception;

    MediaGroupExtend getMedia(String platformTag, String groupTag,String channelTag);
}
