package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.coupon.config.CouponQueryDTO;
import com.eghm.dto.business.coupon.member.MemberCouponQueryPageDTO;
import com.eghm.dto.business.coupon.member.ReceiveCouponDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.CouponService;
import com.eghm.service.business.MemberCouponService;
import com.eghm.vo.business.coupon.CouponListVO;
import com.eghm.vo.business.coupon.MemberCouponBaseVO;
import com.eghm.vo.business.coupon.MemberCouponVO;
import com.eghm.web.annotation.AccessToken;
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

    private final CouponService couponService;

    private final MemberCouponService memberCouponService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<List<CouponListVO>> listPage(@Validated CouponQueryDTO dto) {
        List<CouponListVO> byPage = couponService.getByPage(dto);
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
}
