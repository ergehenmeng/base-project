package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.scenic.ScenicAddRequest;
import com.eghm.dto.business.scenic.ScenicEditRequest;
import com.eghm.dto.business.scenic.ScenicQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.State;
import com.eghm.model.Scenic;
import com.eghm.service.business.ScenicService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.scenic.BaseScenicResponse;
import com.eghm.vo.business.scenic.ScenicDetailResponse;
import com.eghm.vo.business.scenic.ScenicResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛 2022/6/17 19:06
 */
@RestController
@Tag(name="景区")
@AllArgsConstructor
@RequestMapping(value = "/manage/scenic", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScenicController {

    private final ScenicService scenicService;

    @Operation(summary = "列表")
    @GetMapping("/listPage")
    public RespBody<PageData<ScenicResponse>> getByPage(ScenicQueryRequest request) {
        SecurityHolder.getMerchantOptional().ifPresent(request::setMerchantId);
        Page<ScenicResponse> scenicPage = scenicService.getByPage(request);
        return RespBody.success(PageData.toPage(scenicPage));
    }

    @Schema(description = "列表(不分页)")
    @GetMapping("/list")
    public RespBody<List<BaseScenicResponse>> list() {
        List<BaseScenicResponse> scenicList = scenicService.getList(SecurityHolder.getMerchantId());
        return RespBody.success(scenicList);
    }

    @GetMapping("/storeListPage")
    @Operation(summary = "列表含商户信息")
    public RespBody<PageData<BaseStoreResponse>> storeListPage(BaseStoreQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<BaseStoreResponse> listPage = scenicService.getStorePage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @Operation(summary = "新增")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> create(@Validated @RequestBody ScenicAddRequest request) {
        scenicService.createScenic(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody ScenicEditRequest request) {
        scenicService.updateScenic(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @Operation(summary = "详情")
    public RespBody<ScenicDetailResponse> select(@Validated IdDTO request) {
        Scenic scenic = scenicService.selectById(request.getId());
        return RespBody.success(DataUtil.copy(scenic, ScenicDetailResponse.class));
    }

    @PostMapping(value = "/shelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        scenicService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/unShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        scenicService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/platformUnShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        scenicService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        scenicService.deleteById(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/export")
    @Operation(summary = "导出")
    public void export(HttpServletResponse response, ScenicQueryRequest request) {
        SecurityHolder.getMerchantOptional().ifPresent(request::setMerchantId);
        List<ScenicResponse> byPage = scenicService.getList(request);
        EasyExcelUtil.export(response, "景区列表", byPage, ScenicResponse.class);
    }
}
