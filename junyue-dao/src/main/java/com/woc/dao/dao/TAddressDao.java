package com.woc.dao.dao;

import com.woc.common.entity.TAddress;
import com.woc.common.entity.TAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TAddressDao {
    long countByExample(TAddressExample example);

    int deleteByExample(TAddressExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TAddress record);

    int insertSelective(TAddress record);

    List<TAddress> selectByExample(TAddressExample example);

    TAddress selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TAddress record, @Param("example") TAddressExample example);

    int updateByExample(@Param("record") TAddress record, @Param("example") TAddressExample example);

    int updateByPrimaryKeySelective(TAddress record);

    int updateByPrimaryKey(TAddress record);
}