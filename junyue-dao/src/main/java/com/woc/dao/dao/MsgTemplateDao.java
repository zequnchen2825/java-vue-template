package com.woc.dao.dao;

import com.woc.common.entity.MsgTemplate;
import com.woc.common.entity.MsgTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MsgTemplateDao {
    long countByExample(MsgTemplateExample example);

    int deleteByExample(MsgTemplateExample example);

    int deleteByPrimaryKey(String id);

    int insert(MsgTemplate record);

    int insertSelective(MsgTemplate record);

    List<MsgTemplate> selectByExample(MsgTemplateExample example);

    MsgTemplate selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MsgTemplate record, @Param("example") MsgTemplateExample example);

    int updateByExample(@Param("record") MsgTemplate record, @Param("example") MsgTemplateExample example);

    int updateByPrimaryKeySelective(MsgTemplate record);

    int updateByPrimaryKey(MsgTemplate record);
}