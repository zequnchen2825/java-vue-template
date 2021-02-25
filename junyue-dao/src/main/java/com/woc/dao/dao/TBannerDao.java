package com.woc.dao.dao;

import com.woc.common.entity.TBanner;
import com.woc.common.entity.TBannerExample;

import java.security.Key;
import java.util.List;

import com.woc.dao.cache.RedisCache;
import com.woc.dao.cache.RedisTimeEnum;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

@Repository
@CacheConfig(cacheNames = "WOC_BANNER")
public interface TBannerDao {
    long countByExample(TBannerExample example);

    int deleteByExample(TBannerExample example);

    int deleteByPrimaryKey(Integer id);
    @Caching(evict = {@CacheEvict(key = "'schoolId_'+#record.schoolId")})
    int insert(TBanner record);
    @Caching(evict = {@CacheEvict(key = "'schoolId_'+#record.schoolId")})
    int insertSelective(TBanner record);

    List<TBanner> selectByExample(TBannerExample example);
    @Cacheable(key = "#p0")
    TBanner selectByPrimaryKey(Integer id);
    @Caching(evict = {@CacheEvict(key = "#record.id"),@CacheEvict(key = "'schoolId_'+#record.schoolId")})
    int updateByExampleSelective(@Param("record") TBanner record, @Param("example") TBannerExample example);
    @Caching(evict = {@CacheEvict(key = "#record.id"),@CacheEvict(key = "'schoolId_'+#record.schoolId")})
    int updateByExample(@Param("record") TBanner record, @Param("example") TBannerExample example);
    @Caching(evict = {@CacheEvict(key = "#record.id"),@CacheEvict(key = "'schoolId_'+#record.schoolId")})
    int updateByPrimaryKeySelective(TBanner record);
    @Caching(evict = {@CacheEvict(key = "#record.id"),@CacheEvict(key = "'schoolId_'+#record.schoolId")})
    int updateByPrimaryKey(TBanner record);
}