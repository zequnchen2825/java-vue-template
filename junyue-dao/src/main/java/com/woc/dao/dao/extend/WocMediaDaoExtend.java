package com.woc.dao.dao.extend;

import com.woc.common.entity.WocMedia;
import com.woc.common.entity.WocMediaExample;
import com.woc.common.entity.extend.DO.WocMediaExtendDO;
import com.woc.common.entity.extend.WocMediaExtend;
import com.woc.dao.dao.WocMediaDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WocMediaDaoExtend extends WocMediaDao {

    List<WocMediaExtend> findMedia(WocMediaExtendDO wocMedia);

    List<WocMedia> selectSelfByTitle(@Param("title")String title,@Param("platfromId") Integer platfromId);
}