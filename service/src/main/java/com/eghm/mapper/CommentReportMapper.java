package com.eghm.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.comment.CommentReportQueryRequest;
import com.eghm.model.CommentReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.vo.business.comment.CommentReportResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 评论举报记录表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-17
 */
public interface CommentReportMapper extends BaseMapper<CommentReport> {

    /**
     * 分页查询举报列表
     *
     * @param page 分页
     * @param request 查询条件
     * @return 列表
     */
    Page<CommentReportResponse> getByPage(Page<CommentReportResponse> page, @Param("param") CommentReportQueryRequest request);
}
