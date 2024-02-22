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
import com.eghm.enums.ref.State;
import com.eghm.model.ScenicTicket;
import com.eghm.service.business.ScenicTicketService;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.scenic.ticket.ScenicTicketResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2022/6/17
 */
@RestController
@Api(tags = "景区门票")
@AllArgsConstructor
@RequestMapping("/manage/scenic/ticket")
public class ScenicTicketController {

    private final ScenicTicketService scenicTicketService;

    @GetMapping("/listPage")
    @ApiOperation("门票列表")
    public RespBody<PageData<ScenicTicketResponse>> listPage(ScenicTicketQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<ScenicTicketResponse> responsePage = scenicTicketService.getByPage(request);
        return RespBody.success(PageData.toPage(responsePage));
    }

    @GetMapping("/productListPage")
    @ApiOperation("列表含店铺信息")
    public RespBody<PageData<BaseProductResponse>> productListPage(BaseProductQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<BaseProductResponse> listPage = scenicTicketService.getProductPage(request);
        return RespBody.success(PageData.toPage(listPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("创建门票")
    public RespBody<Void> create(@RequestBody @Validated ScenicTicketAddRequest request) {
        scenicTicketService.createTicket(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("更新门票")
    public RespBody<Void> update(@RequestBody @Validated ScenicTicketEditRequest request) {
        scenicTicketService.updateTicket(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("查询门票")
    public RespBody<ScenicTicket> select(@Validated IdDTO dto) {
        ScenicTicket scenicTicket = scenicTicketService.selectByIdRequired(dto.getId());
        return RespBody.success(scenicTicket);
    }

    @PostMapping(value = "/shelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        scenicTicketService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/unShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        scenicTicketService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/platformUnShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        scenicTicketService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        scenicTicketService.deleteById(dto.getId());
        return RespBody.success();
    }
}
