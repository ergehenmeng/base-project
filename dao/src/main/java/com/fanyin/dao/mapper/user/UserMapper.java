package com.fanyin.dao.mapper.user;

import com.fanyin.dao.model.user.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface UserMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(User record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    User selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 根据手机号查询状态正常的用户信息
     * @param mobile 手机号
     * @return 用户信息
     */
    User getByMobile(@Param("mobile")String mobile);

    /**
     * 根据邮箱查询状态正常的用户信息
     * @param email 邮箱
     * @return 用户信息
     */
    User getByEmail(@Param("email")String email);

}