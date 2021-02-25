package com.woc.dao.dao.extend;

import com.woc.common.entity.MediaGroup;
import com.woc.common.entity.MediaGroupExample;
import com.woc.common.entity.extend.DO.MediaGroupExtendDO;
import com.woc.common.entity.extend.MediaGroupExtend;
import com.woc.dao.dao.MediaGroupDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MediaGroupDaoExtend extends MediaGroupDao {

    List<MediaGroupExtend> findMediaGroup(MediaGroupExtendDO mediaGroup);

    MediaGroupExtend getMediaGroupByPCG(@Param("platformTag") String platformTag,@Param("channelTag") String channelTag,@Param("groupTag") String groupTag);
}