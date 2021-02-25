package com.woc.dao.dao;

import com.woc.common.entity.WocMenu;
import com.woc.common.entity.WocMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WocMenuDao {
    long countByExample(WocMenuExample example);

    int deleteByExample(WocMenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WocMenu record);

    int insertSelective(WocMenu record);

    List<WocMenu> selectByExample(WocMenuExample example);

    WocMenu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WocMenu record, @Param("example") WocMenuExample example);

    int updateByExample(@Param("record") WocMenu record, @Param("example") WocMenuExample example);

    int updateByPrimaryKeySelective(WocMenu record);

    int updateByPrimaryKey(WocMenu record);
}