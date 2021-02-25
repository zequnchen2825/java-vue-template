package com.woc.dao.dao.extend;

import com.woc.common.entity.TUser;
import com.woc.common.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TUserDaoExtend {
    List<UserVO> getUserListByCourseId(@Param("schoolId") int schoolId, @Param("courseId") int courseId, @Param("ignoreId") Integer ignoreId);

    int getUserCountByCourseId(@Param("schoolId") int schoolId, @Param("courseId") int courseId, @Param("ignoreId") Integer ignoreId);

    TUser checkAccountBinded(@Param("account")String account, @Param("school")String school,@Param("channelId")Integer channelId);

    List<TUser> findUserAlarmCourseByTime(@Param("alarmTime")String alarmTime,@Param("business")String business);

    TUser getUserByOpenIdAndChannelId(@Param("channelId")Integer channelId, @Param("openid")String openid);

    TUser getUserByIdAndChannelId(@Param("channelId")Integer channelId, @Param("userId")Integer userId);

}
