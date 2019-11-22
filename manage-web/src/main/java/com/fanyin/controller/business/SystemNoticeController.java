package com.fanyin.controller.business;

import com.fanyin.annotation.Mark;
import com.fanyin.dao.model.business.SystemNotice;
import com.fanyin.model.dto.business.notice.NoticeAddRequest;
import com.fanyin.model.dto.business.notice.NoticeEditRequest;
import com.fanyin.model.dto.business.notice.NoticeQueryRequest;
import com.fanyin.model.ext.Paging;
import com.fanyin.model.ext.RespBody;
import com.fanyin.service.common.SystemNoticeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 二哥很猛
 * @date 2019/8/23 13:35
 */
@Controller
public class SystemNoticeController {

    @Autowired
    private SystemNoticeService systemNoticeService;

    /**
     * 系统公告列表查询
     */
    @PostMapping("/business/notice/list_page")
    @ResponseBody
    public Paging<SystemNotice> listPage(NoticeQueryRequest request){
        PageInfo<SystemNotice> byPage = systemNoticeService.getByPage(request);
        return new Paging<>(byPage);
    }

    /**
     * 新增公告信息
     */
    @PostMapping("/business/notice/add")
    @ResponseBody
    @Mark
    public RespBody add(NoticeAddRequest request){
        systemNoticeService.addNotice(request);
        return RespBody.getInstance();
    }

    /**
     * 编辑公告信息
     */
    @PostMapping("/business/notice/edit")
    @ResponseBody
    @Mark
    public RespBody edit(NoticeEditRequest request){
        systemNoticeService.editNotice(request);
        return RespBody.getInstance();
    }

    /**
     * 删除公告信息
     */
    @PostMapping("/business/notice/delete")
    @ResponseBody
    @Mark
    public RespBody delete(Integer id){
        systemNoticeService.deleteNotice(id);
        return RespBody.getInstance();
    }
}
