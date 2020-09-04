package com.eghm.dao.mapper;

import com.eghm.model.dto.notice.NoticeQueryRequest;
import com.eghm.dao.model.SysNotice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysNoticeMapper {

    /**
     * 插入不为空的记录
     */
    int insertSelective(SysNotice record);

    /**
     * 根据主键获取一条数据库记录
     */
    SysNotice selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     */
    int updateByPrimaryKeySelective(SysNotice record);

    /**
     * 根据条件查询
     * @param request 查询条件
     * @return 结果列表
     */
    List<SysNotice> getList(NoticeQueryRequest request);

    /**
     * 获取前多少条公告信息
     * @param noticeLimit 几条公告
     * @return 公告列表
     */
    List<SysNotice> getTopList(@Param("noticeLimit") int noticeLimit);
}