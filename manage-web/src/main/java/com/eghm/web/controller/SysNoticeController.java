package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.SysNotice;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.notice.NoticeAddRequest;
import com.eghm.model.dto.notice.NoticeEditRequest;
import com.eghm.model.dto.notice.NoticeHandleRequest;
import com.eghm.model.dto.notice.NoticeQueryRequest;
import com.eghm.service.common.SysNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @date 2019/8/23 13:35
 */
@RestController
@Api(tags = "公告管理")
@AllArgsConstructor
@RequestMapping("/notice")
public class SysNoticeController {

    private final SysNoticeService sysNoticeService;

    @ApiOperation("公告列表(分页)")
    @GetMapping("/listPage")
    public PageData<SysNotice> listPage(NoticeQueryRequest request) {
        Page<SysNotice> byPage = sysNoticeService.getByPage(request);
        return PageData.toPage(byPage);
    }

    /**
     * 新增公告信息
     */
    @PostMapping("/create")
    @ApiOperation("新增公告信息")
    public RespBody<Void> create(@Validated @RequestBody NoticeAddRequest request) {
        sysNoticeService.create(request);
        return RespBody.success();
    }

    /**
     * 公告编辑页面
     */
    @GetMapping("/select")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @ApiOperation("公告信息查询")
    public SysNotice select(@RequestParam("id") Long id) {
        return sysNoticeService.getById(id);
    }


    @PostMapping("/handle")
    @ApiOperation("公告操作(发布,取消发布,删除)")
    public RespBody<Void> handle(@Validated @RequestBody NoticeHandleRequest request) {
        if (request.getState() == NoticeHandleRequest.PUBLISH) {
            sysNoticeService.publish(request.getId());
        } else if (request.getState() == NoticeHandleRequest.CANCEL_PUBLISH) {
            sysNoticeService.cancelPublish(request.getId());
        } else {
            sysNoticeService.delete(request.getId());
        }
        return RespBody.success();
    }

    /**
     * 编辑公告信息
     */
    @PostMapping("/update")
    @ApiOperation("编辑公告")
    public RespBody<Void> update(@Validated @RequestBody NoticeEditRequest request) {
        sysNoticeService.update(request);
        return RespBody.success();
    }


}
