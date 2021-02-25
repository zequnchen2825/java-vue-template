package com.woc.dao.dao;

import com.woc.common.entity.TBannerGroup;
import com.woc.common.entity.TBannerGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TBannerGroupDao {
    long countByExample(TBannerGroupExample example);

    int deleteByExample(TBannerGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TBannerGroup record);

    int insertSelective(TBannerGroup record);

    List<TBannerGroup> selectByExample(TBannerGroupExample example);

    TBannerGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TBannerGroup record, @Param("example") TBannerGroupExample example);

    int updateByExample(@Param("record") TBannerGroup record, @Param("example") TBannerGroupExample example);

    int updateByPrimaryKeySelective(TBannerGroup record);

    int updateByPrimaryKey(TBannerGroup record);
}