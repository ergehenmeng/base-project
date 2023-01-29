package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.CouponConfig;
import com.eghm.model.dto.business.coupon.config.CouponConfigQueryRequest;
import com.eghm.model.dto.ext.PageData;
import com.eghm.service.business.CouponConfigService;
import com.eghm.web.annotation.Access;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @date 2022/7/18
 */
@RestController
@Api(tags = "优惠券")
@AllArgsConstructor
@RequestMapping("/webapp/coupon")
public class CouponController {

    private final CouponConfigService couponConfigService;

    @GetMapping("/listPage")
    @ApiOperation("优惠券列表")
    @Access
    public PageData<CouponConfig> listPage(@Validated CouponConfigQueryRequest request) {
        Page<CouponConfig> configPage = couponConfigService.getByPage(request);
        return PageData.toPage(configPage);
    }
}
