package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.State;
import com.eghm.model.CouponConfig;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.business.coupon.config.CouponConfigAddRequest;
import com.eghm.model.dto.business.coupon.config.CouponConfigEditRequest;
import com.eghm.model.dto.business.coupon.config.CouponConfigQueryRequest;
import com.eghm.model.dto.business.coupon.user.GrantCouponDTO;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.business.CouponConfigService;
import com.eghm.service.business.UserCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @date 2022/7/18
 */
@RestController
@Api(tags = "优惠券配置")
@AllArgsConstructor
@RequestMapping("/manage/coupon/config")
public class CouponConfigController {

    private final CouponConfigService couponConfigService;

    private final UserCouponService userCouponService;

    @GetMapping("/listPage")
    @ApiOperation("优惠券列表")
    public PageData<CouponConfig> listPage(CouponConfigQueryRequest request) {
        Page<CouponConfig> configPage = couponConfigService.getByPage(request);
        return PageData.toPage(configPage);
    }

    @PostMapping("/create")
    @ApiOperation("创建优惠券")
    public RespBody<Void> create(@RequestBody @Validated CouponConfigAddRequest request) {
        couponConfigService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新优惠券")
    public RespBody<Void> update(@RequestBody @Validated CouponConfigEditRequest request) {
        couponConfigService.update(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("查询优惠券")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    public CouponConfig select(@RequestParam("id") Long id) {
        return couponConfigService.selectById(id);
    }

    @PostMapping("/open")
    @ApiOperation("启用优惠券")
    public RespBody<Void> open(@RequestBody @Validated IdDTO dto) {
        couponConfigService.updateState(dto.getId(), State.SHELVE.getValue());
        return RespBody.success();
    }

    @PostMapping("/close")
    @ApiOperation("禁用优惠券")
    public RespBody<Void> close(@RequestBody @Validated IdDTO dto) {
        couponConfigService.updateState(dto.getId(), State.UN_SHELVE.getValue());
        return RespBody.success();
    }

    @PostMapping("/grant")
    @ApiOperation("发放优惠券")
    public RespBody<Void> grant(@RequestBody @Validated GrantCouponDTO dto) {
        userCouponService.grantCoupon(dto);
        return RespBody.success();
    }
}
