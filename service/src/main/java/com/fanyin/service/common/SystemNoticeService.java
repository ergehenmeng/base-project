package com.fanyin.service.common;

import com.fanyin.model.dto.business.notice.NoticeAddRequest;
import com.fanyin.model.dto.business.notice.NoticeEditRequest;
import com.fanyin.model.dto.business.notice.NoticeQueryRequest;
import com.fanyin.dao.model.business.SystemNotice;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/11/20 19:11
 */
public interface SystemNoticeService {


    /**
     * 获取公告列表,具体多少条由系统参数notice_limit控制
     * @return 公告列表
     */
    List<SystemNotice> getList();

    /**
     * 添加公告
     * @param request 前台参数
     */
    void addNotice(NoticeAddRequest request);

    /**
     * 更新公告
     * @param request 前台参数
     */
    void updateNotice(NoticeEditRequest request);

    /**
     * 删除公告
     * @param id 公告id
     */
    void deleteNotice(Integer id);

    /**
     * 分页查询公告信息
     * @param request 查询条件
     * @return 结果集
     */
    PageInfo<SystemNotice> getByPage(NoticeQueryRequest request);
}

