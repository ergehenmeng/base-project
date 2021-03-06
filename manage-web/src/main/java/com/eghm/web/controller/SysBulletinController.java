package com.eghm.web.controller;

import cn.hutool.core.util.StrUtil;
import com.eghm.common.enums.ErrorCode;
import com.eghm.constants.DictConstant;
import com.eghm.dao.model.SysBulletin;
import com.eghm.model.dto.bulletin.BulletinAddRequest;
import com.eghm.model.dto.bulletin.BulletinEditRequest;
import com.eghm.model.dto.bulletin.BulletinQueryRequest;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.cache.ProxyService;
import com.eghm.service.common.SysBulletinService;
import com.eghm.utils.DataUtil;
import com.eghm.web.annotation.Mark;
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
public class SysBulletinController {

    private SysBulletinService sysBulletinService;

    private ProxyService proxyService;

    @Autowired
    public void setSysBulletinService(SysBulletinService sysBulletinService) {
        this.sysBulletinService = sysBulletinService;
    }

    @Autowired
    public void setProxyService(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    /**
     * 系统公告列表查询
     */
    @GetMapping("/bulletin/list_page")
    @ResponseBody
    public Paging<SysBulletin> listPage(BulletinQueryRequest request) {
        PageInfo<SysBulletin> byPage = sysBulletinService.getByPage(request);
        return DataUtil.convert(byPage, notice -> {
            notice.setClassifyName(proxyService.getDictValue(DictConstant.NOTICE_CLASSIFY, notice.getClassify()));
            return notice;
        });
    }

    /**
     * 新增公告信息
     */
    @PostMapping("/bulletin/add")
    @ResponseBody
    @Mark
    public RespBody<Object> add(BulletinAddRequest request) {
        if (StrUtil.isBlank(request.getOriginalContent())) {
            return RespBody.error(ErrorCode.TEXT_CONTENT_EMPTY);
        }
        sysBulletinService.addNotice(request);
        return RespBody.success();
    }

    /**
     * 公告编辑页面
     */
    @GetMapping("/bulletin/edit_page")
    public String editPage(Model model, Long id) {
        SysBulletin notice = sysBulletinService.getById(id);
        model.addAttribute("notice", notice);
        return "bulletin/edit_page";
    }

    /**
     * 发布公告
     */
    @PostMapping("/bulletin/publish")
    @ResponseBody
    @Mark
    public RespBody<Object> publish(Long id) {
        sysBulletinService.publish(id);
        return RespBody.success();
    }

    /**
     * 取消发布的公告
     */
    @PostMapping("/bulletin/cancel_publish")
    @ResponseBody
    @Mark
    public RespBody<Object> cancelPublish(Long id) {
        sysBulletinService.cancelPublish(id);
        return RespBody.success();
    }

    /**
     * 编辑公告信息
     */
    @PostMapping("/bulletin/edit")
    @ResponseBody
    @Mark
    public RespBody<Object> edit(BulletinEditRequest request) {
        if (StrUtil.isBlank(request.getOriginalContent())) {
            return RespBody.error(ErrorCode.TEXT_CONTENT_EMPTY);
        }
        sysBulletinService.editNotice(request);
        return RespBody.success();
    }

    /**
     * 删除公告信息
     */
    @PostMapping("/bulletin/delete")
    @ResponseBody
    @Mark
    public RespBody<Object> delete(Long id) {
        sysBulletinService.deleteNotice(id);
        return RespBody.success();
    }

    /**
     * 富文本预览
     */
    @GetMapping("/bulletin/preview")
    public String preview(Model model, Long id) {
        SysBulletin notice = sysBulletinService.getById(id);
        if (notice != null) {
            model.addAttribute("response", notice.getContent());
        }
        return "query_page";
    }
}
