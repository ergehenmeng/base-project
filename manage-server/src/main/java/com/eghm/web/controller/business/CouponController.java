package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.coupon.config.CouponAddRequest;
import com.eghm.dto.business.coupon.config.CouponEditRequest;
import com.eghm.dto.business.coupon.config.CouponQueryRequest;
import com.eghm.dto.business.coupon.member.GrantCouponDTO;
import com.eghm.dto.business.coupon.member.MemberCouponQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.State;
import com.eghm.model.Coupon;
import com.eghm.service.business.CouponService;
import com.eghm.service.business.MemberCouponService;
import com.eghm.vo.business.coupon.CouponResponse;
import com.eghm.vo.business.coupon.MemberCouponResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2022/7/18
 */
@RestController
@Api(tags = "优惠券配置")
@AllArgsConstructor
@RequestMapping("/manage/coupon")
public class CouponController {

    private final CouponService couponService;

    private final MemberCouponService memberCouponService;

    @GetMapping("/listPage")
    @ApiOperation("优惠券列表")
    public RespBody<PageData<CouponResponse>> listPage(@Validated CouponQueryRequest request) {
        Page<CouponResponse> configPage = couponService.getByPage(request);
        return RespBody.success(PageData.toPage(configPage));
    }

    @PostMapping("/create")
    @ApiOperation("创建优惠券")
    public RespBody<Void> create(@RequestBody @Validated CouponAddRequest request) {
        couponService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新优惠券")
    public RespBody<Void> update(@RequestBody @Validated CouponEditRequest request) {
        couponService.update(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("查询优惠券")
    public RespBody<Coupon> select(@Validated IdDTO dto) {
        Coupon config = couponService.selectByIdRequired(dto.getId());
        return RespBody.success(config);
    }

    @PostMapping("/open")
    @ApiOperation("启用优惠券")
    public RespBody<Void> open(@RequestBody @Validated IdDTO dto) {
        couponService.updateState(dto.getId(), State.SHELVE.getValue());
        return RespBody.success();
    }

    @PostMapping("/close")
    @ApiOperation("禁用优惠券")
    public RespBody<Void> close(@RequestBody @Validated IdDTO dto) {
        couponService.updateState(dto.getId(), State.UN_SHELVE.getValue());
        return RespBody.success();
    }

    @PostMapping("/grant")
    @ApiOperation("发放优惠券")
    public RespBody<Void> grant(@RequestBody @Validated GrantCouponDTO dto) {
        memberCouponService.grantCoupon(dto);
        return RespBody.success();
    }

    @GetMapping("/receivePage")
    @ApiOperation("领取详情")
    public RespBody<PageData<MemberCouponResponse>> receivePage(@Validated MemberCouponQueryRequest request) {
        Page<MemberCouponResponse> byPage = memberCouponService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }
}
