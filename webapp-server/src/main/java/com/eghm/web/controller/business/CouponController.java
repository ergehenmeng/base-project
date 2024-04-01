package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.coupon.config.CouponQueryDTO;
import com.eghm.dto.business.coupon.member.MemberCouponQueryPageDTO;
import com.eghm.dto.business.coupon.member.ReceiveCouponDTO;
import com.eghm.dto.business.coupon.product.CouponProductDTO;
import com.eghm.dto.business.item.ItemCouponQueryDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.VisitType;
import com.eghm.service.business.CouponService;
import com.eghm.service.business.ItemService;
import com.eghm.service.business.MemberCouponService;
import com.eghm.vo.business.coupon.CouponVO;
import com.eghm.vo.business.coupon.MemberCouponBaseVO;
import com.eghm.vo.business.coupon.MemberCouponVO;
import com.eghm.vo.business.item.ItemVO;
import com.eghm.web.annotation.AccessToken;
import com.eghm.web.annotation.VisitRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "优惠券")
@AllArgsConstructor
@RequestMapping(value = "/webapp/coupon", produces = MediaType.APPLICATION_JSON_VALUE)
public class CouponController {

    private final ItemService itemService;

    private final CouponService couponService;

    private final MemberCouponService memberCouponService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    @VisitRecord(VisitType.MARKETING)
    public RespBody<List<CouponVO>> listPage(@Validated CouponQueryDTO dto) {
        List<CouponVO> byPage = couponService.getByPage(dto);
        return RespBody.success(byPage);
    }

    @PostMapping(value = "/member/receive", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("领取优惠券")
    @AccessToken
    public RespBody<Void> receive(@RequestBody @Validated ReceiveCouponDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        memberCouponService.receiveCoupon(dto);
        return RespBody.success();
    }

    @GetMapping("/member/listPage")
    @ApiOperation("我的优惠券列表")
    @AccessToken
    public RespBody<List<MemberCouponVO>> listPage(@Validated MemberCouponQueryPageDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        List<MemberCouponVO> voList = memberCouponService.memberCouponPage(dto);
        return RespBody.success(voList);
    }

    @GetMapping("/member/choose")
    @ApiOperation("选择商品可以使用的优惠券")
    @AccessToken
    public RespBody<List<MemberCouponBaseVO>> choose(@Validated IdDTO dto) {
        List<MemberCouponBaseVO> selectCoupon = memberCouponService.selectCoupon(ApiHolder.getMemberId(), dto.getId());
        return RespBody.success(selectCoupon);
    }

    @GetMapping("/detail")
    @ApiOperation("优惠券详情")
    public RespBody<CouponVO> detail(@Validated IdDTO dto) {
        CouponVO detail = couponService.getDetail(dto.getId());
        return RespBody.success(detail);
    }

    @GetMapping("/couponList")
    @ApiOperation("商品页可以领取的优惠券(全部)")
    public RespBody<List<CouponVO>> couponList(@Validated CouponProductDTO dto) {
        List<CouponVO> voList = couponService.getProductCoupon(dto);
        return RespBody.success(voList);
    }

    @GetMapping("/itemCouponScope")
    @ApiOperation("优惠券匹配的商品列表(零售)")
    public RespBody<List<ItemVO>> itemCouponScope(@Validated ItemCouponQueryDTO dto) {
        List<ItemVO> voList = itemService.getCouponScopeByPage(dto);
        return RespBody.success(voList);
    }

}
