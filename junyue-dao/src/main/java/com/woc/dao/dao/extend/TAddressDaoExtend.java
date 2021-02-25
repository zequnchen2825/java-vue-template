package com.woc.dao.dao.extend;

import com.woc.common.entity.extend.TAddressExtend;
import com.woc.dao.dao.TAddressDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TAddressDaoExtend extends TAddressDao {

    List<TAddressExtend> findAddress(@Param("userId") Integer userId);
}
