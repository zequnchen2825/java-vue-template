package com.woc.dao.dao;

import com.woc.common.entity.MediaGroup;
import com.woc.common.entity.MediaGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaGroupDao {
    long countByExample(MediaGroupExample example);

    int deleteByExample(MediaGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MediaGroup record);

    int insertSelective(MediaGroup record);

    List<MediaGroup> selectByExample(MediaGroupExample example);

    MediaGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MediaGroup record, @Param("example") MediaGroupExample example);

    int updateByExample(@Param("record") MediaGroup record, @Param("example") MediaGroupExample example);

    int updateByPrimaryKeySelective(MediaGroup record);

    int updateByPrimaryKey(MediaGroup record);
}