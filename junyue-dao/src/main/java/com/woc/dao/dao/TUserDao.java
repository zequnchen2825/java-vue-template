package com.woc.dao.dao;

import com.woc.common.entity.TUser;
import com.woc.common.entity.TUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
@CacheConfig(cacheNames = "WOC_USER")
public interface TUserDao {
    long countByExample(TUserExample example);

    int deleteByExample(TUserExample example);
    @CacheEvict(key="#record.id")
    int deleteByPrimaryKey(Integer id);

    int insert(TUser record);

    int insertSelective(TUser record);

    List<TUser> selectByExample(TUserExample example);
    @Cacheable(key = "#p0")
    TUser selectByPrimaryKey(Integer id);
    @CacheEvict(key="#record.id")
    int updateByExampleSelective(@Param("record") TUser record, @Param("example") TUserExample example);
    @CacheEvict(key="#record.id")
    int updateByExample(@Param("record") TUser record, @Param("example") TUserExample example);
    @CacheEvict(key="#record.id")
    int updateByPrimaryKeySelective(TUser record);
    @CacheEvict(key="#record.id")
    int updateByPrimaryKey(TUser record);
}