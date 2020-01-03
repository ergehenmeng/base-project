package com.eghm.controller.business;

import com.eghm.annotation.Mark;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.utils.StringUtil;
import com.eghm.constants.DictConstant;
import com.eghm.dao.model.business.SystemNotice;
import com.eghm.model.dto.business.notice.NoticeAddRequest;
import com.eghm.model.dto.business.notice.NoticeEditRequest;
import com.eghm.model.dto.business.notice.NoticeQueryRequest;
import com.eghm.model.ext.Paging;
import com.eghm.model.ext.RespBody;
import com.eghm.service.cache.CacheProxyService;
import com.eghm.service.common.SystemNoticeService;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private CacheProxyService cacheProxyService;

    /**
     * 系统公告列表查询
     */
    @PostMapping("/business/notice/list_page")
    @ResponseBody
    public Paging<SystemNotice> listPage(NoticeQueryRequest request) {
        PageInfo<SystemNotice> byPage = systemNoticeService.getByPage(request);
        return DataUtil.transform(byPage, notice -> {
            notice.setClassifyName(cacheProxyService.getDictValue(DictConstant.NOTICE_CLASSIFY, notice.getClassify()));
            return notice;
        });
    }

    /**
     * 新增公告信息
     */
    @PostMapping("/business/notice/add")
    @ResponseBody
    @Mark
    public RespBody add(NoticeAddRequest request) {
        if (StringUtil.isBlank(request.getOriginalContent())) {
            return RespBody.error(ErrorCode.TEXT_CONTENT_EMPTY);
        }
        systemNoticeService.addNotice(request);
        return RespBody.getInstance();
    }

    /**
     * 公告编辑页面
     */
    @GetMapping("/business/notice/edit_page")
    public String editPage(Model model, Integer id) {
        SystemNotice notice = systemNoticeService.getById(id);
        model.addAttribute("notice", notice);
        return "business/notice/edit_page";
    }

    /**
     * 发布公告
     */
    @PostMapping("/business/notice/publish")
    @ResponseBody
    @Mark
    public RespBody publish(Integer id) {
        systemNoticeService.publish(id);
        return RespBody.getInstance();
    }

    /**
     * 取消发布的公告
     */
    @PostMapping("/business/notice/cancel_publish")
    @ResponseBody
    @Mark
    public RespBody cancelPublish(Integer id) {
        systemNoticeService.cancelPublish(id);
        return RespBody.getInstance();
    }

    /**
     * 编辑公告信息
     */
    @PostMapping("/business/notice/edit")
    @ResponseBody
    @Mark
    public RespBody edit(NoticeEditRequest request) {
        if (StringUtil.isBlank(request.getOriginalContent())) {
            return RespBody.error(ErrorCode.TEXT_CONTENT_EMPTY);
        }
        systemNoticeService.editNotice(request);
        return RespBody.getInstance();
    }

    /**
     * 删除公告信息
     */
    @PostMapping("/business/notice/delete")
    @ResponseBody
    @Mark
    public RespBody delete(Integer id) {
        systemNoticeService.deleteNotice(id);
        return RespBody.getInstance();
    }

    /**
     * 富文本预览
     */
    @GetMapping("/business/notice/preview")
    public String preview(Model model, Integer id) {
        SystemNotice notice = systemNoticeService.getById(id);
        if (notice != null) {
            model.addAttribute("response", notice.getContent());
        }
        return "query_page";
    }
}