package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.operate.notice.NoticeQueryRequest;
import com.eghm.model.SysNotice;
import com.eghm.vo.operate.notice.NoticeResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysNoticeMapper extends BaseMapper<SysNotice> {

    /**
     * 分页查询公告信息
     *
     * @param page 分页
     * @param request 查询条件
     * @return 列表
     */
    Page<NoticeResponse> getByPage(Page<NoticeResponse> page, @Param("param") NoticeQueryRequest request);

    /**
     * 获取前多少条公告信息
     *
     * @param noticeLimit 几条公告
     * @return 公告列表
     */
    List<SysNotice> getTopList(@Param("noticeLimit") int noticeLimit);
}