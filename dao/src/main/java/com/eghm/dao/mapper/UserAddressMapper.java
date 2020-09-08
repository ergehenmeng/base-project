package com.eghm.dao.mapper;

import com.eghm.dao.model.UserAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface UserAddressMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(UserAddress record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    UserAddress selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(UserAddress record);

    /**
     * 获取用户地址信息
     * @param userId userId
     * @return 地址列表
     */
    List<UserAddress> getByUserId(@Param("userId")Integer userId);

    /**
     * 更新地址状态
     * @param userId userId
     * @param state 状态
     * @return 批量多少条
     */
    int updateState(@Param("userId")Integer userId, @Param("state")Byte state);
}