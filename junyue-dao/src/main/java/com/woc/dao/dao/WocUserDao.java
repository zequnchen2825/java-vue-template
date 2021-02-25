package com.woc.dao.dao;

import com.woc.common.entity.WocUser;
import com.woc.common.entity.WocUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WocUserDao {
    long countByExample(WocUserExample example);

    int deleteByExample(WocUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WocUser record);

    int insertSelective(WocUser record);

    List<WocUser> selectByExample(WocUserExample example);

    WocUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WocUser record, @Param("example") WocUserExample example);

    int updateByExample(@Param("record") WocUser record, @Param("example") WocUserExample example);

    int updateByPrimaryKeySelective(WocUser record);

    int updateByPrimaryKey(WocUser record);
}