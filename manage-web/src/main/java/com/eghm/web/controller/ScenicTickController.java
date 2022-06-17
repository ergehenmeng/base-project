package com.eghm.web.controller;

import com.eghm.dao.model.ScenicTicket;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.ticket.ScenicTicketQueryRequest;
import com.eghm.model.vo.ticket.ScenicTicketResponse;
import com.eghm.service.business.ScenicTicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wyb 2022/6/17 19:05
 */
@RestController
@Api(tags = "景区门票")
@AllArgsConstructor
@RequestMapping("/ticket")
public class ScenicTickController {

    private final ScenicTicketService scenicTicketService;

    @GetMapping("/getByPage")
    @ApiOperation("门票列表")
    public RespBody<ScenicTicketResponse> getByPage(ScenicTicketQueryRequest request) {
        scenicTicketService.getByPage(request)
    }

}
