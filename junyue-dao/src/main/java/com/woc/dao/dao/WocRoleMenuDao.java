package com.woc.dao.dao;

import com.woc.common.entity.WocRoleMenu;
import com.woc.common.entity.WocRoleMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WocRoleMenuDao {
    long countByExample(WocRoleMenuExample example);

    int deleteByExample(WocRoleMenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WocRoleMenu record);

    int insertSelective(WocRoleMenu record);

    List<WocRoleMenu> selectByExample(WocRoleMenuExample example);

    WocRoleMenu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WocRoleMenu record, @Param("example") WocRoleMenuExample example);

    int updateByExample(@Param("record") WocRoleMenu record, @Param("example") WocRoleMenuExample example);

    int updateByPrimaryKeySelective(WocRoleMenu record);

    int updateByPrimaryKey(WocRoleMenu record);
}