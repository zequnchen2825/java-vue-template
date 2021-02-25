package com.woc.dao.dao;

import com.woc.common.entity.WocChannel;
import com.woc.common.entity.WocChannelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

@Repository
@CacheConfig(cacheNames = "WOC_CHANNEL")
public interface WocChannelDao {
    long countByExample(WocChannelExample example);

    int deleteByExample(WocChannelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WocChannel record);

    int insertSelective(WocChannel record);

    List<WocChannel> selectByExample(WocChannelExample example);
    @Cacheable(key="#p0")
    WocChannel selectByPrimaryKey(Integer id);
    @Caching(evict = {@CacheEvict(key = "#record.id"),@CacheEvict(key = "#record.schoolId+'_'+#record.platformId")
            ,@CacheEvict(key = "#record.channelTag+'_'+#record.platformId")})
    int updateByExampleSelective(@Param("record") WocChannel record, @Param("example") WocChannelExample example);
    @Caching(evict = {@CacheEvict(key = "#record.id"),@CacheEvict(key = "#record.schoolId+'_'+#record.platformId")
            ,@CacheEvict(key = "#record.channelTag+'_'+#record.platformId")})
    int updateByExample(@Param("record") WocChannel record, @Param("example") WocChannelExample example);

    @Caching(evict = {@CacheEvict(key = "#record.id"),@CacheEvict(key = "#record.schoolId+'_'+#record.platformId")
            ,@CacheEvict(key = "#record.channelTag+'_'+#record.platformId")})
    int updateByPrimaryKeySelective(WocChannel record);
    @Caching(evict = {@CacheEvict(key = "#record.id"),@CacheEvict(key = "#record.schoolId+'_'+#record.platformId")
            ,@CacheEvict(key = "#record.channelTag+'_'+#record.platformId")})
    int updateByPrimaryKey(WocChannel record);
}