package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.restaurant.RestaurantDTO;
import com.eghm.dto.business.restaurant.RestaurantQueryDTO;
import com.eghm.dto.business.restaurant.voucher.VoucherQueryDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.VisitType;
import com.eghm.service.business.RestaurantService;
import com.eghm.service.business.VoucherService;
import com.eghm.service.business.VoucherTagService;
import com.eghm.vo.business.restaurant.*;
import com.eghm.web.annotation.VisitRecord;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/6/30
 */
@RestController
@Tag(name="餐饮门店")
@AllArgsConstructor
@RequestMapping(value = "/webapp/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    private final VoucherService voucherService;

    private final RestaurantService restaurantService;

    private final VoucherTagService voucherTagService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<List<RestaurantVO>> listPage(@ParameterObject RestaurantQueryDTO dto) {
        List<RestaurantVO> byPage = restaurantService.getByPage(dto);
        return RespBody.success(byPage);
    }

    @GetMapping("/detail")
    @Operation(summary = "详情")
    public RespBody<RestaurantDetailVO> detail(@Validated @ParameterObject RestaurantDTO dto) {
        RestaurantDetailVO detail = restaurantService.detailById(dto);
        return RespBody.success(detail);
    }

    @GetMapping("/tag")
    @Operation(summary = "标签")
    @Parameter(name = "restaurantId", description = "店铺id")
    public RespBody<List<VoucherTagVO>> tag(@RequestParam("restaurantId") Long restaurantId) {
        List<VoucherTagVO> tagList = voucherTagService.getTagList(restaurantId);
        return RespBody.success(tagList);
    }

    @GetMapping("/voucher/listPage")
    @Operation(summary = "餐饮券列表")
    @VisitRecord(VisitType.PRODUCT_LIST)
    public RespBody<List<VoucherVO>> voucherListPage(@Validated @ParameterObject VoucherQueryDTO dto) {
        List<VoucherVO> voList = voucherService.getByPage(dto);
        return RespBody.success(voList);
    }

    @GetMapping("/voucher/detail")
    @Operation(summary = "餐饮券详情")
    @VisitRecord(VisitType.PRODUCT_DETAIL)
    public RespBody<VoucherDetailVO> voucherDetail(@Validated @ParameterObject IdDTO dto) {
        VoucherDetailVO detail = voucherService.getDetail(dto.getId());
        return RespBody.success(detail);
    }
}
