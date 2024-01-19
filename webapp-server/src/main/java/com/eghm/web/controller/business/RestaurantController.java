package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.restaurant.RestaurantQueryDTO;
import com.eghm.dto.business.restaurant.voucher.VoucherQueryDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.RestaurantService;
import com.eghm.service.business.VoucherService;
import com.eghm.vo.business.restaurant.RestaurantDetailVO;
import com.eghm.vo.business.restaurant.RestaurantVO;
import com.eghm.vo.business.restaurant.VoucherDetailVO;
import com.eghm.vo.business.restaurant.VoucherVO;
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
 * @since 2022/6/30
 */
@RestController
@Api(tags = "餐饮门店")
@AllArgsConstructor
@RequestMapping("/webapp/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    private final VoucherService voucherService;

    @GetMapping("/listPage")
    @ApiOperation("商家列表")
    public RespBody<List<RestaurantVO>> listPage(RestaurantQueryDTO dto) {
        List<RestaurantVO> byPage = restaurantService.getByPage(dto);
        return RespBody.success(byPage);
    }

    @GetMapping("/detail")
    @ApiOperation("店铺详情")
    public RespBody<RestaurantDetailVO> detail(@Validated IdDTO dto) {
        RestaurantDetailVO detail = restaurantService.detailById(dto.getId());
        return RespBody.success(detail);
    }

    @GetMapping("/voucher/listPage")
    @ApiOperation("店铺餐饮券列表")
    public RespBody<List<VoucherVO>> voucherListPage(@Validated VoucherQueryDTO dto) {
        List<VoucherVO> voList = voucherService.getByPage(dto);
        return RespBody.success(voList);
    }

    @GetMapping("/voucher/detail")
    @ApiOperation("餐饮券详情")
    public RespBody<VoucherDetailVO> voucherDetail(@Validated IdDTO dto) {
        VoucherDetailVO detail = voucherService.getDetail(dto.getId());
        return RespBody.success(detail);
    }
}
