package com.woc.dao.dao;

import com.woc.common.entity.WocPlatform;
import com.woc.common.entity.WocPlatformExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Repository;

@Repository
@CacheConfig(cacheNames = "WOC_PLATFORM")
public interface WocPlatformDao {
    long countByExample(WocPlatformExample example);
    @CacheEvict(cacheNames = "WOC_PLATFROM_PAGE",key = "'*'")
    int deleteByExample(WocPlatformExample example);
    @CacheEvict(cacheNames = "WOC_PLATFROM_PAGE",key = "'*'")
    int deleteByPrimaryKey(Integer id);
    @CacheEvict(cacheNames = "WOC_PLATFROM_PAGE",key = "'*'")
    int insert(WocPlatform record);
    @CacheEvict(cacheNames = "WOC_PLATFROM_PAGE",key = "'*'")
    int insertSelective(WocPlatform record);

    List<WocPlatform> selectByExample(WocPlatformExample example);
    @Cacheable(key = "#p0")
    WocPlatform selectByPrimaryKey(Integer id);
    @CacheEvict(cacheNames = "WOC_PLATFROM_PAGE",key = "'*'")
    int updateByExampleSelective(@Param("record") WocPlatform record, @Param("example") WocPlatformExample example);
    @CacheEvict(cacheNames = "WOC_PLATFROM_PAGE",key = "'*'")
    int updateByExample(@Param("record") WocPlatform record, @Param("example") WocPlatformExample example);

    @Caching(evict = {@CacheEvict(key = "#record.id"),@CacheEvict(key = "#record.platformTag")})
    int updateByPrimaryKeySelective(WocPlatform record);
    @Caching(evict = {@CacheEvict(key = "#record.id"),@CacheEvict(key = "#record.platformTag")})
    int updateByPrimaryKey(WocPlatform record);
}