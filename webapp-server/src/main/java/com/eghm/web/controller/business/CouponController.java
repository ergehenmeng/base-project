package com.eghm.web.controller.business;

import com.eghm.dto.business.coupon.config.CouponQueryDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.CouponService;
import com.eghm.vo.business.coupon.CouponListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/18
 */
@RestController
@Api(tags = "优惠券中心")
@AllArgsConstructor
@RequestMapping("/webapp/coupon")
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/listPage")
    @ApiOperation("优惠券列表")
    public RespBody<List<CouponListVO>> listPage(CouponQueryDTO dto) {
        List<CouponListVO> byPage = couponService.getByPage(dto);
        return RespBody.success(byPage);
    }

}
