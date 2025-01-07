package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.scenic.ScenicDetailDTO;
import com.eghm.dto.business.scenic.ScenicQueryDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.VisitType;
import com.eghm.service.business.ScenicService;
import com.eghm.service.business.ScenicTicketService;
import com.eghm.vo.business.scenic.ScenicDetailVO;
import com.eghm.vo.business.scenic.ScenicVO;
import com.eghm.vo.business.scenic.ticket.TicketVO;
import com.eghm.web.annotation.VisitRecord;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
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
@Tag(name="景区")
@AllArgsConstructor
@RequestMapping(value = "/webapp/scenic", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScenicController {

    private final ScenicService scenicService;

    private final ScenicTicketService scenicTicketService;

    @GetMapping("/listPage")
    @Operation(summary = "景区列表")
    public RespBody<List<ScenicVO>> listPage(ScenicQueryDTO dto) {
        List<ScenicVO> byPage = scenicService.getByPage(dto);
        return RespBody.success(byPage);
    }

    @GetMapping("/detail")
    @Operation(summary = "景区详情")
    @VisitRecord(VisitType.PRODUCT_LIST)
    public RespBody<ScenicDetailVO> scenicDetail(@Validated ScenicDetailDTO dto) {
        ScenicDetailVO detail = scenicService.detailById(dto);
        return RespBody.success(detail);
    }

    @GetMapping("/ticket/detail")
    @Operation(summary = "景区门票详情")
    @VisitRecord(VisitType.PRODUCT_DETAIL)
    public RespBody<TicketVO> ticketDetail(@Validated IdDTO dto) {
        TicketVO vo = scenicTicketService.detailById(dto.getId());
        return RespBody.success(vo);
    }

}
