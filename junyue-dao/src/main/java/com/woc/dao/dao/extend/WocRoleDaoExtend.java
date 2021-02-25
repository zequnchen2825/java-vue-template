package com.woc.dao.dao.extend;

import com.woc.common.entity.WocRole;
import com.woc.dao.dao.WocRoleDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WocRoleDaoExtend extends WocRoleDao {


    List<WocRole> findRolesByUserId(@Param("userId") Integer id);
}