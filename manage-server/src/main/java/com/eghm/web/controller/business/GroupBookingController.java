package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.group.GroupBookingAddRequest;
import com.eghm.dto.business.group.GroupBookingEditRequest;
import com.eghm.dto.business.group.GroupBookingQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.GroupBookingService;
import com.eghm.vo.business.group.GroupBookingDetailResponse;
import com.eghm.vo.business.group.GroupBookingResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2024/1/24
 */

@RestController
@Tag(name="拼团活动")
@AllArgsConstructor
@RequestMapping(value = "/manage/group/booking", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupBookingController {

    private final GroupBookingService groupBookingService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<GroupBookingResponse>> listPage(GroupBookingQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<GroupBookingResponse> byPage = groupBookingService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建")
    public RespBody<Void> create(@RequestBody @Validated GroupBookingAddRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        groupBookingService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@RequestBody @Validated GroupBookingEditRequest request) {
        groupBookingService.update(request);
        return RespBody.success();
    }

    @GetMapping("/detail")
    @Operation(summary = "详情")
    public RespBody<GroupBookingDetailResponse> detail(@Validated IdDTO dto) {
        GroupBookingDetailResponse detail = groupBookingService.detail(dto.getId());
        return RespBody.success(detail);
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        groupBookingService.delete(dto.getId());
        return RespBody.success();
    }

}
