package com.woc.dao.dao;

import com.woc.common.entity.WocRole;
import com.woc.common.entity.WocRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WocRoleDao {
    long countByExample(WocRoleExample example);

    int deleteByExample(WocRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WocRole record);

    int insertSelective(WocRole record);

    List<WocRole> selectByExample(WocRoleExample example);

    WocRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WocRole record, @Param("example") WocRoleExample example);

    int updateByExample(@Param("record") WocRole record, @Param("example") WocRoleExample example);

    int updateByPrimaryKeySelective(WocRole record);

    int updateByPrimaryKey(WocRole record);
}