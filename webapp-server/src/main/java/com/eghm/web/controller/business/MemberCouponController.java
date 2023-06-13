package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.coupon.member.MemberCouponQueryPageDTO;
import com.eghm.dto.business.coupon.member.ReceiveCouponDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.MemberCouponService;
import com.eghm.vo.business.coupon.MemberCouponBaseVO;
import com.eghm.vo.business.coupon.MemberCouponVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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
    @AccessToken
    public RespBody<Void> receive(@RequestBody @Validated ReceiveCouponDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        memberCouponService.receiveCoupon(dto);
        return RespBody.success();
    }

    @GetMapping("/listPage")
    @ApiOperation("优惠券列表")
    @AccessToken
    public List<MemberCouponVO> listPage(@Validated MemberCouponQueryPageDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        return memberCouponService.memberCouponPage(dto);
    }

    @GetMapping("/choose")
    @ApiOperation("选择优惠券")
    @AccessToken
    public List<MemberCouponBaseVO> choose(@Validated IdDTO dto) {
        return memberCouponService.selectCoupon(ApiHolder.getMemberId(), dto.getId());
    }
}
