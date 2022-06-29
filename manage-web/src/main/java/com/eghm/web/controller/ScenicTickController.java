package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.ScenicTicket;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.IdStateDTO;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.ticket.ScenicTicketAddRequest;
import com.eghm.model.dto.ticket.ScenicTicketEditRequest;
import com.eghm.model.dto.ticket.ScenicTicketQueryRequest;
import com.eghm.model.vo.ticket.ScenicTicketResponse;
import com.eghm.service.business.ScenicTicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛 2022/6/17 19:05
 */
@RestController
@Api(tags = "景区门票")
@AllArgsConstructor
@RequestMapping("/ticket")
public class ScenicTickController {

    private final ScenicTicketService scenicTicketService;

    @GetMapping("/getByPage")
    @ApiOperation("门票列表")
    public PageData<ScenicTicketResponse> getByPage(ScenicTicketQueryRequest request) {
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

    @PostMapping("/select")
    @ApiOperation("查询门票")
    public ScenicTicket select(@RequestBody @Validated IdDTO dto) {
        return scenicTicketService.selectById(dto.getId());
    }

    @PostMapping("/updateState")
    @ApiOperation("更新门票上下架状态")
    public RespBody<Void> updateState(@RequestBody @Validated IdStateDTO dto) {
        scenicTicketService.updateState(dto.getId(), dto.getState());
        return RespBody.success();
    }

}
