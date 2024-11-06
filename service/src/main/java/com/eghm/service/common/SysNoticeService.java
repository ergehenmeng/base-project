package com.eghm.service.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.operate.notice.NoticeAddRequest;
import com.eghm.dto.operate.notice.NoticeEditRequest;
import com.eghm.dto.operate.notice.NoticeQueryRequest;
import com.eghm.model.SysNotice;
import com.eghm.vo.notice.NoticeDetailVO;
import com.eghm.vo.notice.NoticeResponse;
import com.eghm.vo.notice.NoticeVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2018/11/20 19:11
 */
public interface SysNoticeService {

    /**
     * 分页查询公告信息
     *
     * @param request 查询条件
     * @return 结果集
     */
    Page<NoticeResponse> getByPage(NoticeQueryRequest request);

    /**
     * 获取公告前几条标题信息,具体多少条由系统参数notice_limit控制
     *
     * @return 公告列表
     */
    List<NoticeVO> getList();

    /**
     * 分页查询列表
     *
     * @param query 分页信息
     * @return 公告列表
     */
    List<NoticeVO> getList(PagingQuery query);

    /**
     * 添加公告
     *
     * @param request 前台参数
     */
    void create(NoticeAddRequest request);

    /**
     * 更新公告
     *
     * @param request 前台参数
     */
    void update(NoticeEditRequest request);

    /**
     * 删除公告
     *
     * @param id 公告id
     */
    void delete(Long id);

    /**
     * 主键查询公告信息
     *
     * @param id id
     * @return 公告信息
     */
    SysNotice getById(Long id);

    /**
     * 查询公告详情
     *
     * @param id id
     * @return 详细信息
     */
    NoticeDetailVO detailById(Long id);

    /**
     * 主键查询公告信息
     *
     * @param id id
     * @return 公告信息
     */
    SysNotice getByIdRequired(Long id);

    /**
     * 发布公告
     *
     * @param id id主键
     */
    void publish(Long id);

    /**
     * 取消发布
     *
     * @param id 主键
     */
    void cancelPublish(Long id);
}

