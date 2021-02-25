package com.woc.dao.dao.extend;

import com.woc.common.entity.WocRole;
import com.woc.common.entity.WocUser;
import com.woc.common.entity.extend.WocUserExtend;
import com.woc.dao.dao.WocUserDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WocUserDaoExtend extends WocUserDao {

    List<WocUserExtend> selectByPlatformTag(@Param("platformTag") String platformTag, @Param("account") String account);

    List<WocRole> findUserRoleList(@Param("userId") Integer userId);
}
