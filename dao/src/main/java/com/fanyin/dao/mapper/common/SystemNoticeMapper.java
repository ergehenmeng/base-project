package com.fanyin.dao.mapper.common;

import com.fanyin.model.dto.business.notice.NoticeQueryRequest;
import com.fanyin.dao.model.business.SystemNotice;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SystemNoticeMapper {

    /**
     * 插入不为空的记录
     */
    int insertSelective(SystemNotice record);

    /**
     * 根据主键获取一条数据库记录
     */
    SystemNotice selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     */
    int updateByPrimaryKeySelective(SystemNotice record);

    /**
     * 根据条件查询
     * @param request 查询条件
     * @return 结果列表
     */
    List<SystemNotice> getList(NoticeQueryRequest request);
}