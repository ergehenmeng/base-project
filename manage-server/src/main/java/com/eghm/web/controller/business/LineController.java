package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.line.LineAddRequest;
import com.eghm.dto.business.line.LineEditRequest;
import com.eghm.dto.business.line.LineQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.RepastType;
import com.eghm.enums.State;
import com.eghm.model.Line;
import com.eghm.model.LineDayConfig;
import com.eghm.service.business.LineDayConfigService;
import com.eghm.service.business.LineService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.line.LineDayConfigResponse;
import com.eghm.vo.business.line.LineDetailResponse;
import com.eghm.vo.business.line.LineResponse;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/11/26
 */
@RestController
@Tag(name="线路")
@AllArgsConstructor
@RequestMapping(value = "/manage/line", produces = MediaType.APPLICATION_JSON_VALUE)
public class LineController {

    private final LineService lineService;

    private final LineDayConfigService lineDayConfigService;

    @Operation(summary = "查询线路列表")
    @GetMapping("/listPage")
    public RespBody<PageData<LineResponse>> getByPage(LineQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<LineResponse> scenicPage = lineService.getByPage(request);
        return RespBody.success(PageData.toPage(scenicPage));
    }

    @GetMapping("/productListPage")
    @Operation(summary = "列表(含店铺)")
    public RespBody<PageData<BaseProductResponse>> productListPage(BaseProductQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<BaseProductResponse> listPage = lineService.getProductPage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @Operation(summary = "新增")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RespBody<Void> create(@Validated @RequestBody LineAddRequest request) {
        lineService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@Validated @RequestBody LineEditRequest request) {
        lineService.update(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @Operation(summary = "详情")
    public RespBody<LineDetailResponse> select(@Validated IdDTO request) {
        Line line = lineService.selectByIdRequired(request.getId());
        LineDetailResponse response = DataUtil.copy(line, LineDetailResponse.class);
        List<LineDayConfig> dayList = lineDayConfigService.getByLineId(request.getId());
        response.setConfigList(DataUtil.copy(dayList, config -> {
            LineDayConfigResponse configResponse = DataUtil.copy(config, LineDayConfigResponse.class);
            configResponse.setRepastList(this.parseRepast(config.getRepast()));
            return configResponse;
        }));
        // 虚拟销量需要计算
        response.setVirtualNum(line.getTotalNum() - line.getSaleNum());
        return RespBody.success(response);
    }

    @PostMapping(value = "/shelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        lineService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/unShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        lineService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/platformUnShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        lineService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        lineService.deleteById(dto.getId());
        return RespBody.success();
    }

    @Operation(summary = "导出")
    @GetMapping("/export")
    public void export(HttpServletResponse response, LineQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<LineResponse> byPage = lineService.getList(request);
        EasyExcelUtil.export(response, "线路信息", byPage, LineResponse.class);
    }

    /**
     * 包含就餐 1:早餐 2:午餐 4:晚餐
     *
     * @param repast 最大7
     * @return list
     */
    private List<Integer> parseRepast(Integer repast) {
        if (repast == null) {
            return Lists.newArrayList();
        }
        List<Integer> list = Lists.newArrayList();
        for (RepastType value : RepastType.values()) {
            if ((repast & value.getValue()) == value.getValue()) {
                list.add(value.getValue());
                return list;
            }
        }
        return list;
    }
}
