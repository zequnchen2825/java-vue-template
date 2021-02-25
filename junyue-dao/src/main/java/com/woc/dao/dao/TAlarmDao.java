package com.woc.dao.dao;

import com.woc.common.entity.TAlarm;
import com.woc.common.entity.TAlarmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TAlarmDao {
    long countByExample(TAlarmExample example);

    int deleteByExample(TAlarmExample example);

    int deleteByPrimaryKey(Integer studentId);

    int insert(TAlarm record);

    int insertSelective(TAlarm record);

    List<TAlarm> selectByExample(TAlarmExample example);

    TAlarm selectByPrimaryKey(Integer studentId);

    int updateByExampleSelective(@Param("record") TAlarm record, @Param("example") TAlarmExample example);

    int updateByExample(@Param("record") TAlarm record, @Param("example") TAlarmExample example);

    int updateByPrimaryKeySelective(TAlarm record);

    int updateByPrimaryKey(TAlarm record);
}