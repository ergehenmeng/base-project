package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.venue.VenueDTO;
import com.eghm.dto.business.venue.VenuePriceQueryDTO;
import com.eghm.dto.business.venue.VenueQueryDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.VisitType;
import com.eghm.service.business.VenueService;
import com.eghm.service.business.VenueSitePriceService;
import com.eghm.service.business.VenueSiteService;
import com.eghm.vo.business.venue.VenueDetailVO;
import com.eghm.vo.business.venue.VenueSitePriceVO;
import com.eghm.vo.business.venue.VenueSiteVO;
import com.eghm.vo.business.venue.VenueVO;
import com.eghm.web.annotation.VisitRecord;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/2/2
 */

@RestController
@Tag(name="场馆信息")
@AllArgsConstructor
@RequestMapping(value = "/webapp/venue", produces = MediaType.APPLICATION_JSON_VALUE)
public class VenueController {

    private final VenueService venueService;

    private final VenueSiteService venueSiteService;

    private final VenueSitePriceService venueSitePriceService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<List<VenueVO>> listPage(@Validated VenueQueryDTO dto) {
        List<VenueVO> voList = venueService.getByPage(dto);
        return RespBody.success(voList);
    }

    @GetMapping("/detail")
    @Operation(summary = "详情")
    public RespBody<VenueDetailVO> detail(@Validated VenueDTO dto) {
        VenueDetailVO detail = venueService.getDetail(dto);
        return RespBody.success(detail);
    }

    @GetMapping("/siteList")
    @Operation(summary = "场地列表")
    @VisitRecord(VisitType.PRODUCT_LIST)
    public RespBody<List<VenueSiteVO>> siteList(@Validated IdDTO dto) {
        List<VenueSiteVO> voList = venueSiteService.getList(dto.getId());
        return RespBody.success(voList);
    }

    @GetMapping("/priceList")
    @Operation(summary = "场次价格")
    public RespBody<List<VenueSitePriceVO>> priceList(@Validated VenuePriceQueryDTO dto) {
        List<VenueSitePriceVO> voList = venueSitePriceService.getPriceList(dto.getVenueSiteId(), dto.getNowDate());
        return RespBody.success(voList);
    }

}
