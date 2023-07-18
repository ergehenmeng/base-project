package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.scenic.ScenicDetailDTO;
import com.eghm.dto.business.scenic.ScenicQueryDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.ScenicService;
import com.eghm.service.business.ScenicTicketService;
import com.eghm.vo.business.scenic.ScenicListVO;
import com.eghm.vo.business.scenic.ScenicVO;
import com.eghm.vo.business.scenic.ticket.TicketVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/1/5
 */
@RestController
@Api(tags = "景区")
@AllArgsConstructor
@RequestMapping("/webapp/scenic")
public class ScenicController {

    private final ScenicService scenicService;

    private final ScenicTicketService scenicTicketService;

    @GetMapping("/listPage")
    @ApiOperation("景区列表")
    public RespBody<List<ScenicListVO>> listPage(ScenicQueryDTO dto) {
        List<ScenicListVO> byPage = scenicService.getByPage(dto);
        return RespBody.success(byPage);
    }

    @GetMapping("/detail")
    @ApiOperation("景区详情")
    public RespBody<ScenicVO> scenicDetail(@Validated ScenicDetailDTO dto) {
        ScenicVO detail = scenicService.detailById(dto.getScenicId(), dto.getLongitude(), dto.getLatitude());
        return RespBody.success(detail);
    }

    @GetMapping("/ticket/detail")
    @ApiOperation("景区门票详情")
    public RespBody<TicketVO> ticketDetail(@Validated IdDTO dto) {
        TicketVO vo = scenicTicketService.detailById(dto.getId());
        return RespBody.success(vo);
    }

}
