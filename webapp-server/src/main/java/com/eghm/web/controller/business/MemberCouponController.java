package com.eghm.web.controller.business;

import com.eghm.dto.business.coupon.member.ReceiveCouponDTO;
import com.eghm.dto.business.coupon.member.MemberCouponQueryPageDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.vo.coupon.MemberCouponBaseVO;
import com.eghm.vo.coupon.MemberCouponVO;
import com.eghm.service.business.MemberCouponService;
import com.eghm.web.annotation.Access;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/18
 */
@RestController
@Api(tags = "我的优惠券")
@AllArgsConstructor
@RequestMapping("/webapp/member/coupon")
public class MemberCouponController {

    private final MemberCouponService memberCouponService;

    @PostMapping("/receive")
    @ApiOperation("优惠券领取")
    @Access
    public RespBody<Void> receive(@RequestBody @Validated ReceiveCouponDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        memberCouponService.receiveCoupon(dto);
        return RespBody.success();
    }

    @GetMapping("/listPage")
    @ApiOperation("优惠券列表")
    @Access
    public List<MemberCouponVO> listPage(@Validated MemberCouponQueryPageDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        return memberCouponService.memberCouponPage(dto);
    }

    @GetMapping("/choose")
    @ApiOperation("选择优惠券")
    @ApiImplicitParam(name = "productId", value = "商品id", required = true)
    @Access
    public List<MemberCouponBaseVO> choose(@Param("productId") Long productId) {
        return memberCouponService.selectCoupon(ApiHolder.getMemberId(), productId);
    }
}
