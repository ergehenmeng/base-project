package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.SortByDTO;
import com.eghm.dto.business.VoucherTagAddRequest;
import com.eghm.dto.business.VoucherTagEditRequest;
import com.eghm.dto.business.VoucherTagQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.VoucherTagService;
import com.eghm.vo.business.restaurant.TagSelectResponse;
import com.eghm.vo.business.restaurant.VoucherTagResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @author 二哥很猛
* @since 2024-10-09
*/
@RestController
@Tag(name="餐饮券标签管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/voucher/tag", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoucherTagController {

    private final VoucherTagService voucherTagService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<VoucherTagResponse>> listPage(VoucherTagQueryRequest request) {
        Page<VoucherTagResponse> byPage = voucherTagService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/list")
    @Operation(summary = "列表(不分页)")
    public RespBody<List<TagSelectResponse>> list(@Validated IdDTO request) {
        List<TagSelectResponse> list = voucherTagService.getList(request.getId());
        return RespBody.success(list);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@Validated @RequestBody VoucherTagAddRequest request) {
        voucherTagService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody VoucherTagEditRequest request) {
        voucherTagService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/sort", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "排序")
    public RespBody<Void> sort(@RequestBody @Validated SortByDTO dto) {
        voucherTagService.sortBy(dto.getId(), dto.getSortBy());
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO request) {
        voucherTagService.delete(request.getId());
        return RespBody.success();
    }
}