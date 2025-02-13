package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.scenic.ticket.ScenicTicketAddRequest;
import com.eghm.dto.business.scenic.ticket.ScenicTicketEditRequest;
import com.eghm.dto.business.scenic.ticket.ScenicTicketQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.State;
import com.eghm.service.business.ScenicTicketService;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.scenic.ticket.TicketBaseResponse;
import com.eghm.vo.business.scenic.ticket.TicketDetailResponse;
import com.eghm.vo.business.scenic.ticket.TicketResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/6/17
 */
@RestController
@Tag(name="景区门票")
@AllArgsConstructor
@RequestMapping(value = "/manage/scenic/ticket", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScenicTicketController {

    private final ScenicTicketService scenicTicketService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<TicketResponse>> listPage(ScenicTicketQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<TicketResponse> responsePage = scenicTicketService.getByPage(request);
        return RespBody.success(PageData.toPage(responsePage));
    }

    @GetMapping("/productListPage")
    @Operation(summary = "列表(含店铺)")
    public RespBody<PageData<BaseProductResponse>> productListPage(BaseProductQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<BaseProductResponse> listPage = scenicTicketService.getProductPage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@RequestBody @Validated ScenicTicketAddRequest request) {
        scenicTicketService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@RequestBody @Validated ScenicTicketEditRequest request) {
        scenicTicketService.update(request);
        return RespBody.success();
    }

    @GetMapping("/list")
    @Operation(summary = "列表(不含组合票)")
    @Parameter(name = "scenicId", description = "所属景区ID", required = true)
    @Parameter(name = "id", description = "门票ID", required = true)
    public RespBody<List<TicketBaseResponse>> list(@RequestParam("id") Long id, @RequestParam("scenicId") Long scenicId) {
        List<TicketBaseResponse> responseList = scenicTicketService.getList(SecurityHolder.getMerchantId(), scenicId, id);
        return RespBody.success(responseList);
    }

    @GetMapping("/select")
    @Operation(summary = "详情")
    public RespBody<TicketDetailResponse> select(@Validated IdDTO dto) {
        TicketDetailResponse response = scenicTicketService.detail(dto.getId());
        return RespBody.success(response);
    }

    @PostMapping(value = "/shelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        scenicTicketService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/unShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        scenicTicketService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/platformUnShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        scenicTicketService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        scenicTicketService.deleteById(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/export")
    @Operation(summary = "导出")
    public void export(HttpServletResponse response, ScenicTicketQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<TicketResponse> byPage = scenicTicketService.getList(request);
        EasyExcelUtil.export(response, "门票列表", byPage, TicketResponse.class);
    }
}
