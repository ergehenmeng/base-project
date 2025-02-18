package com.eghm.web.controller.business;

import com.eghm.dto.business.travel.TravelAgencyDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.TravelAgencyService;
import com.eghm.vo.business.line.TravelDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @since 2022/11/26
 */
@RestController
@Api(tags = "旅行社")
@AllArgsConstructor
@RequestMapping(value = "/webapp/travel", produces = MediaType.APPLICATION_JSON_VALUE)
public class TravelAgencyController {

    private final TravelAgencyService travelAgencyService;

    @GetMapping("/detail")
    @ApiOperation("详情")
    public RespBody<TravelDetailVO> detail(@Validated TravelAgencyDTO dto) {
        TravelDetailVO detail = travelAgencyService.detail(dto);
        return RespBody.success(detail);
    }

}
