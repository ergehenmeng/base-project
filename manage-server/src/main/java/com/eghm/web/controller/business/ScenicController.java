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
import com.eghm.enums.ref.State;
import com.eghm.model.Scenic;
import com.eghm.service.business.ScenicService;
import com.eghm.vo.business.base.BaseStoreResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛 2022/6/17 19:06
 */
@RestController
@Api(tags = "景区")
@AllArgsConstructor
@RequestMapping("/manage/scenic")
public class ScenicController {

    private final ScenicService scenicService;

    @ApiOperation("查询景区列表")
    @GetMapping("/listPage")
    public RespBody<PageData<Scenic>> getByPage(ScenicQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<Scenic> scenicPage = scenicService.getByPage(request);
        return RespBody.success(PageData.toPage(scenicPage));
    }

    @GetMapping("/storeListPage")
    @ApiOperation("列表含商户信息")
    public RespBody<PageData<BaseStoreResponse>> storeListPage(BaseStoreQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<BaseStoreResponse> listPage = scenicService.getStorePage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @ApiOperation("创建景区")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> create(@Validated @RequestBody ScenicAddRequest request) {
        scenicService.createScenic(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("更新景区")
    public RespBody<Void> update(@Validated @RequestBody ScenicEditRequest request) {
        scenicService.updateScenic(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("详情")
    public RespBody<Scenic> select(@Validated IdDTO request) {
        Scenic scenic = scenicService.selectById(request.getId());
        return RespBody.success(scenic);
    }

    @PostMapping(value = "/shelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        scenicService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/unShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        scenicService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/platformUnShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        scenicService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        scenicService.deleteById(dto.getId());
        return RespBody.success();
    }
}
