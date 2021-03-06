package com.eghm.dao.mapper;

import com.eghm.dao.model.UserNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface UserNoticeMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(UserNotice record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    UserNotice selectByPrimaryKey(Long id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(UserNotice record);

    /**
     * 列表查询
     * @param userId
     * @return
     */
    List<UserNotice> getList(@Param("userId")Long userId);

    /**
     * 更新 delete read字段
     * @param notice  不为空更新
     * @return
     */
    int updateNotice(UserNotice notice);

}