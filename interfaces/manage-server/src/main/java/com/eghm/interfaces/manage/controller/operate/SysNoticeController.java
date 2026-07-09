package com.eghm.interfaces.manage.controller.operate;

import com.eghm.dto.ext.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.operate.notice.NoticeAddRequest;
import com.eghm.dto.operate.notice.NoticeEditRequest;
import com.eghm.dto.operate.notice.NoticeQueryRequest;
import com.eghm.domain.operate.model.SysNotice;
import com.eghm.application.operate.service.SysNoticeService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.operate.notice.NoticeDetailResponse;
import com.eghm.vo.operate.notice.NoticeResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2019/8/23 13:35
 */
@RestController
@AllArgsConstructor
@Tag(name = "公告管理")
@RequestMapping(value = "/manage/notice", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysNoticeController {

    private final SysNoticeService sysNoticeService;

    @Operation(summary = "列表")
    @GetMapping("/listPage")
    public RespBody<PageData<NoticeResponse>> listPage(@ParameterObject NoticeQueryRequest request) {
        Page<NoticeResponse> byPage = sysNoticeService.getByPage(request);
        return RespBody.success(PageData.convert(byPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@Validated @RequestBody NoticeAddRequest request) {
        sysNoticeService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody NoticeEditRequest request) {
        sysNoticeService.update(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @Operation(summary = "查看")
    public RespBody<NoticeDetailResponse> select(@ParameterObject @Validated IdDTO dto) {
        SysNotice notice = sysNoticeService.getByIdRequired(dto.getId());
        return RespBody.success(DataUtil.copy(notice, NoticeDetailResponse.class));
    }

    @PostMapping(value = "/publish", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "发布")
    public RespBody<Void> publish(@Validated @RequestBody IdDTO request) {
        sysNoticeService.publish(request.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "取消发布")
    public RespBody<Void> cancel(@Validated @RequestBody IdDTO request) {
        sysNoticeService.cancelPublish(request.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO request) {
        sysNoticeService.delete(request.getId());
        return RespBody.success();
    }

}
