package com.eghm.web.controller.business;

import com.eghm.model.dto.business.coupon.config.CouponQueryDTO;
import com.eghm.model.vo.coupon.CouponListVO;
import com.eghm.service.business.CouponConfigService;
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

    private final CouponConfigService couponConfigService;

    @GetMapping("/listPage")
    @ApiOperation("优惠券列表")
    public List<CouponListVO> listPage(CouponQueryDTO dto) {
        return couponConfigService.getByPage(dto);
    }

}
