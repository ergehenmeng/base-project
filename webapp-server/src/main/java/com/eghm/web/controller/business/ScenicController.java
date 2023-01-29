package com.eghm.web.controller.business;

import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.business.scenic.ScenicDetailDTO;
import com.eghm.model.dto.business.scenic.ScenicQueryDTO;
import com.eghm.model.vo.scenic.ScenicListVO;
import com.eghm.model.vo.scenic.ScenicVO;
import com.eghm.model.vo.scenic.ticket.TicketVO;
import com.eghm.service.business.ScenicService;
import com.eghm.service.business.ScenicTicketService;
import com.eghm.web.annotation.SkipAccess;
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
    @SkipAccess
    public List<ScenicListVO> listPage(ScenicQueryDTO dto) {
        return scenicService.getByPage(dto);
    }

    @GetMapping("/detail")
    @ApiOperation("景区详情")
    @SkipAccess
    public ScenicVO scenicDetail(@Validated ScenicDetailDTO dto) {
        return scenicService.detailById(dto.getScenicId(), dto.getLongitude(), dto.getLatitude());
    }

    @GetMapping("/ticket/detail")
    @ApiOperation("景区门票详情")
    @SkipAccess
    public TicketVO ticketDetail(@Validated IdDTO dto) {
        return scenicTicketService.detailById(dto.getId());
    }

}
