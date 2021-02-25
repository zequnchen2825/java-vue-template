package com.woc.dao.dao;

import com.woc.common.entity.WocMedia;
import com.woc.common.entity.WocMediaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WocMediaDao {
    long countByExample(WocMediaExample example);

    int deleteByExample(WocMediaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WocMedia record);

    int insertSelective(WocMedia record);

    List<WocMedia> selectByExample(WocMediaExample example);

    WocMedia selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WocMedia record, @Param("example") WocMediaExample example);

    int updateByExample(@Param("record") WocMedia record, @Param("example") WocMediaExample example);

    int updateByPrimaryKeySelective(WocMedia record);

    int updateByPrimaryKey(WocMedia record);
}