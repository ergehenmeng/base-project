package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.scenic.ticket.ScenicTicketAddRequest;
import com.eghm.dto.business.scenic.ticket.ScenicTicketEditRequest;
import com.eghm.dto.business.scenic.ticket.ScenicTicketQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.PlatformState;
import com.eghm.enums.ref.State;
import com.eghm.model.ScenicTicket;
import com.eghm.service.business.ScenicTicketService;
import com.eghm.vo.business.scenic.ticket.ScenicTicketResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @date 2022/6/17
 */
@RestController
@Api(tags = "景区门票")
@AllArgsConstructor
@RequestMapping("/manage/scenic/ticket")
public class ScenicTickController {

    private final ScenicTicketService scenicTicketService;

    @GetMapping("/listPage")
    @ApiOperation("门票列表")
    public PageData<ScenicTicketResponse> listPage(ScenicTicketQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<ScenicTicketResponse> responsePage = scenicTicketService.getByPage(request);
        return PageData.toPage(responsePage);
    }

    @PostMapping("/create")
    @ApiOperation("创建门票")
    public RespBody<Void> create(@RequestBody @Validated ScenicTicketAddRequest request) {
        scenicTicketService.createTicket(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新门票")
    public RespBody<Void> update(@RequestBody @Validated ScenicTicketEditRequest request) {
        scenicTicketService.updateTicket(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("查询门票")
    public ScenicTicket select(@Validated IdDTO dto) {
        return scenicTicketService.selectByIdRequired(dto.getId());
    }

    @PostMapping("/shelves")
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        scenicTicketService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/unShelves")
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        scenicTicketService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformAudit")
    @ApiOperation("平台上架审核")
    public RespBody<Void> updateState(@RequestBody @Validated IdDTO dto) {
        scenicTicketService.updateAuditState(dto.getId(), PlatformState.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformUnShelves")
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        scenicTicketService.updateAuditState(dto.getId(), PlatformState.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        scenicTicketService.deleteById(dto.getId());
        return RespBody.success();
    }
}
