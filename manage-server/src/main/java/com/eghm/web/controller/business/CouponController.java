package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.coupon.config.CouponAddRequest;
import com.eghm.dto.business.coupon.config.CouponEditRequest;
import com.eghm.dto.business.coupon.config.CouponQueryRequest;
import com.eghm.dto.business.coupon.member.GrantCouponDTO;
import com.eghm.dto.business.coupon.member.MemberCouponQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.State;
import com.eghm.service.business.CouponService;
import com.eghm.service.business.MemberCouponService;
import com.eghm.vo.business.coupon.CouponBaseResponse;
import com.eghm.vo.business.coupon.CouponDetailResponse;
import com.eghm.vo.business.coupon.CouponResponse;
import com.eghm.vo.business.coupon.MemberCouponResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/18
 */
@RestController
@Tag(name="优惠券配置")
@AllArgsConstructor
@RequestMapping(value = "/manage/coupon", produces = MediaType.APPLICATION_JSON_VALUE)
public class CouponController {

    private final CouponService couponService;

    private final MemberCouponService memberCouponService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<CouponResponse>> listPage(@Validated CouponQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<CouponResponse> configPage = couponService.getByPage(request);
        return RespBody.success(PageData.toPage(configPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增")
    public RespBody<Void> create(@RequestBody @Validated CouponAddRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        couponService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑")
    public RespBody<Void> update(@RequestBody @Validated CouponEditRequest request) {
        couponService.update(request);
        return RespBody.success();
    }

    @GetMapping("/select")
    @Operation(summary = "详情")
    public RespBody<CouponDetailResponse> select(@Validated IdDTO dto) {
        CouponDetailResponse coupon = couponService.getById(dto.getId());
        return RespBody.success(coupon);
    }

    @PostMapping(value = "/open", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "启用")
    public RespBody<Void> open(@RequestBody @Validated IdDTO dto) {
        couponService.updateState(dto.getId(), State.SHELVE.getValue());
        return RespBody.success();
    }

    @PostMapping(value = "/close", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "禁用")
    public RespBody<Void> close(@RequestBody @Validated IdDTO dto) {
        couponService.updateState(dto.getId(), State.UN_SHELVE.getValue());
        return RespBody.success();
    }

    @PostMapping(value = "/grant", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "发放")
    public RespBody<Void> grant(@RequestBody @Validated GrantCouponDTO dto) {
        memberCouponService.grantCoupon(dto);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        couponService.delete(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/receivePage")
    @Operation(summary = "领取列表")
    public RespBody<PageData<MemberCouponResponse>> receivePage(@Validated MemberCouponQueryRequest request) {
        Page<MemberCouponResponse> byPage = memberCouponService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/grantList")
    @Operation(summary = "优惠券列表(手动发放)")
    public RespBody<List<CouponBaseResponse>> grantList() {
        List<CouponBaseResponse> byPage = couponService.getList(2);
        return RespBody.success(byPage);
    }

}
