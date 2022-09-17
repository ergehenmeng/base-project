package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.CouponMode;
import com.eghm.dao.model.CouponConfig;
import com.eghm.model.dto.business.coupon.config.CouponConfigQueryRequest;
import com.eghm.model.dto.business.coupon.user.GrantCouponDTO;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.business.CouponConfigService;
import com.eghm.service.business.UserCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @date 2022/7/18
 */
@RestController
@Api(tags = "优惠券领取")
@AllArgsConstructor
@RequestMapping("/manage/coupon/user")
public class UserCouponController {

    private final UserCouponService userCouponService;

    private final CouponConfigService couponConfigService;

    @PostMapping("/grant")
    @ApiOperation("发放优惠券")
    public RespBody<Void> grant(@RequestBody @Validated GrantCouponDTO dto) {
        userCouponService.grantCoupon(dto);
        return RespBody.success();
    }

    @GetMapping("/listPage")
    @ApiOperation("可以发放的优惠券列表")
    public PageData<CouponConfig> listPage(CouponConfigQueryRequest request) {
        // TODO 返回前端的字段有点多, 后期可以优化一下
        request.setMode(CouponMode.GRANT.getValue());
        request.setInStock(true);
        Page<CouponConfig> configPage = couponConfigService.getByPage(request);
        return PageData.toPage(configPage);
    }

}
