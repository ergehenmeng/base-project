package com.eghm.service.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.SysNotice;
import com.eghm.dto.notice.NoticeAddRequest;
import com.eghm.dto.notice.NoticeEditRequest;
import com.eghm.dto.notice.NoticeQueryRequest;
import com.eghm.vo.notice.TopNoticeVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/11/20 19:11
 */
public interface SysNoticeService {


    /**
     * 获取公告前几条标题信息,具体多少条由系统参数notice_limit控制
     * @return 公告列表
     */
    List<TopNoticeVO> getList();

    /**
     * 添加公告
     * @param request 前台参数
     */
    void create(NoticeAddRequest request);

    /**
     * 更新公告
     * @param request 前台参数
     */
    void update(NoticeEditRequest request);

    /**
     * 删除公告
     * @param id 公告id
     */
    void delete(Long id);

    /**
     * 分页查询公告信息
     * @param request 查询条件
     * @return 结果集
     */
    Page<SysNotice> getByPage(NoticeQueryRequest request);

    /**
     * 主键查询公告信息
     * @param id id
     * @return 公告信息
     */
    SysNotice getById(Long id);

    /**
     * 主键查询公告信息
     * @param id id
     * @return 公告信息
     */
    SysNotice getByIdRequired(Long id);

    /**
     * 发布公告
     * @param id id主键
     */
    void publish(Long id);

    /**
     * 取消发布
     * @param id 主键
     */
    void cancelPublish(Long id);
}

