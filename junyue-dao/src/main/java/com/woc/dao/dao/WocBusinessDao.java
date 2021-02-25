package com.woc.dao.dao;

import com.woc.common.entity.WocBusiness;
import com.woc.common.entity.WocBusinessExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

@Repository
@CacheConfig(cacheNames = "WOC_BUSINESS")
public interface WocBusinessDao {
    long countByExample(WocBusinessExample example);

    int deleteByExample(WocBusinessExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WocBusiness record);

    int insertSelective(WocBusiness record);

    List<WocBusiness> selectByExample(WocBusinessExample example);
    @Cacheable(key = "#p0")
    WocBusiness selectByPrimaryKey(Integer id);
    @Caching(evict = {@CacheEvict(key = "#record.id"),@CacheEvict(key="record.businessTag+'_'+#record.id")})
    int updateByExampleSelective(@Param("record") WocBusiness record, @Param("example") WocBusinessExample example);
    @Caching(evict = {@CacheEvict(key = "#record.id"),@CacheEvict(key="record.businessTag+'_'+#record.id")})
    int updateByExample(@Param("record") WocBusiness record, @Param("example") WocBusinessExample example);
    @Caching(evict = {@CacheEvict(key = "#record.id"),@CacheEvict(key="record.businessTag+'_'+#record.id")})
    int updateByPrimaryKeySelective(WocBusiness record);
    @Caching(evict = {@CacheEvict(key = "#record.id"),@CacheEvict(key="record.businessTag+'_'+#record.id")})
    int updateByPrimaryKey(WocBusiness record);
}