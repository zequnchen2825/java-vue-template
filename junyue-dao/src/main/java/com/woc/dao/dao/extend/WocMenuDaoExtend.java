package com.woc.dao.dao.extend;

import com.woc.common.entity.WocMenu;
import com.woc.common.entity.extend.WocMenuExtend;
import com.woc.dao.dao.WocMenuDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WocMenuDaoExtend extends WocMenuDao {

    List<WocMenuExtend> findMenuByRolesIdAndLevel(@Param("roleIds") String roleIds, @Param("level") Integer level, @Param("pid") Integer pid);


}