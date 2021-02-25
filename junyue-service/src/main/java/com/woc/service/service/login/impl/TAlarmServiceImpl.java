package com.woc.service.service.login.impl;

import com.woc.common.entity.TAlarm;
import com.woc.dao.dao.TAlarmDao;
import com.woc.service.service.login.TAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TAlarmServiceImpl implements TAlarmService {

    @Autowired
    private TAlarmDao tAlarmDao;
    @Override
    public int saveOrUpdateAlarm(TAlarm tAlarm) {

        TAlarm oldAlarm = tAlarmDao.selectByPrimaryKey(tAlarm.getStudentId());
        if(oldAlarm == null){
            return tAlarmDao.insert(tAlarm);
        }else{

            return tAlarmDao.updateByPrimaryKeySelective(tAlarm);
        }


    }

    @Override
    public TAlarm findAlarmByUserId(Integer id) {
        TAlarm tAlarm = tAlarmDao.selectByPrimaryKey(id);
        if(tAlarm ==null){
            tAlarm = new TAlarm();
            tAlarm.setStudentId(id);
            tAlarmDao.insert(tAlarm);
            tAlarm= tAlarmDao.selectByPrimaryKey(id);
        }
        return tAlarm;
    }
}
