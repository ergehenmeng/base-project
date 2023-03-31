package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.UserAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface UserAddressMapper extends BaseMapper<UserAddress> {

    /**
     * 获取用户地址信息
     * @param userId userId
     * @return 地址列表
     */
    List<UserAddress> getByUserId(@Param("userId")Long userId);

    /**
     * 更新地址状态
     * @param userId userId
     * @param state 状态
     * @return 批量多少条
     */
    int updateState(@Param("userId")Long userId, @Param("state")Integer state);

    /**
     * 主键+userId更新地址
     * @param address address
     * @return 1
     */
    int updateByUserId(UserAddress address);
}