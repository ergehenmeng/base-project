package com.eghm.service.common;

import com.eghm.model.dto.business.notice.NoticeAddRequest;
import com.eghm.model.dto.business.notice.NoticeEditRequest;
import com.eghm.model.dto.business.notice.NoticeQueryRequest;
import com.eghm.dao.model.business.SysNotice;
import com.eghm.model.vo.notice.TopNoticeVO;
import com.github.pagehelper.PageInfo;

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
    void addNotice(NoticeAddRequest request);

    /**
     * 更新公告
     * @param request 前台参数
     */
    void editNotice(NoticeEditRequest request);

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
    PageInfo<SysNotice> getByPage(NoticeQueryRequest request);

    /**
     * 主键查询公告信息
     * @param id id
     * @return 公告信息
     */
    SysNotice getById(Integer id);

    /**
     * 发布公告
     * @param id id主键
     */
    void publish(Integer id);

    /**
     * 取消发布
     * @param id 主键
     */
    void cancelPublish(Integer id);
}

