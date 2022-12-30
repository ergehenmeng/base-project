package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.SysNotice;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.notice.NoticeAddRequest;
import com.eghm.model.dto.notice.NoticeEditRequest;
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
@RequestMapping("/manage/notice")
public class SysNoticeController {

    private final SysNoticeService sysNoticeService;

    @ApiOperation("公告列表")
    @GetMapping("/listPage")
    public PageData<SysNotice> listPage(NoticeQueryRequest request) {
        Page<SysNotice> byPage = sysNoticeService.getByPage(request);
        return PageData.toPage(byPage);
    }

    @PostMapping("/create")
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody NoticeAddRequest request) {
        sysNoticeService.create(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    @ApiOperation("查看")
    public SysNotice select(@RequestParam("id") Long id) {
        return sysNoticeService.getByIdRequired(id);
    }

    @PostMapping("/publish")
    @ApiOperation("发布")
    public RespBody<Void> publish(@Validated @RequestBody IdDTO request) {
        sysNoticeService.publish(request.getId());
        return RespBody.success();
    }

    @PostMapping("/cancel")
    @ApiOperation("取消发布")
    public RespBody<Void> cancel(@Validated @RequestBody IdDTO request) {
        sysNoticeService.cancelPublish(request.getId());
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO request) {
        sysNoticeService.delete(request.getId());
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("编辑")
    public RespBody<Void> update(@Validated @RequestBody NoticeEditRequest request) {
        sysNoticeService.update(request);
        return RespBody.success();
    }
}
