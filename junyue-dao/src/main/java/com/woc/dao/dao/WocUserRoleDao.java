package com.woc.dao.dao;

import com.woc.common.entity.WocUserRole;
import com.woc.common.entity.WocUserRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WocUserRoleDao {
    long countByExample(WocUserRoleExample example);

    int deleteByExample(WocUserRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WocUserRole record);

    int insertSelective(WocUserRole record);

    List<WocUserRole> selectByExample(WocUserRoleExample example);

    WocUserRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WocUserRole record, @Param("example") WocUserRoleExample example);

    int updateByExample(@Param("record") WocUserRole record, @Param("example") WocUserRoleExample example);

    int updateByPrimaryKeySelective(WocUserRole record);

    int updateByPrimaryKey(WocUserRole record);
}