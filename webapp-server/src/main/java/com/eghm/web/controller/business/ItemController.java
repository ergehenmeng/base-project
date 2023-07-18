package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.item.ItemCouponQueryDTO;
import com.eghm.dto.business.item.ItemQueryDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.CouponConfigService;
import com.eghm.service.business.ItemService;
import com.eghm.vo.business.coupon.CouponListVO;
import com.eghm.vo.business.item.ItemListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2023/1/23
 */

@RestController
@Api(tags = "商品信息")
@AllArgsConstructor
@RequestMapping("/webapp/item")
public class ItemController {

    private final ItemService itemService;

    private final CouponConfigService couponConfigService;

    @GetMapping("/listPage")
    @ApiOperation("商品列表")
    public RespBody<List<ItemListVO>> listPage(ItemQueryDTO dto) {
        List<ItemListVO> byPage = itemService.getByPage(dto);
        return RespBody.success(byPage);
    }

    @GetMapping("/recommend")
    @ApiOperation("商品推荐列表")
    public RespBody<List<ItemListVO>> recommend() {
        List<ItemListVO> recommend = itemService.getRecommend();
        return RespBody.success(recommend);
    }

    @GetMapping("/coupon")
    @ApiOperation("商品页可以领取的优惠券")
    public RespBody<List<CouponListVO>> item(@Validated IdDTO dto) {
        List<CouponListVO> voList = couponConfigService.getItemCoupon(dto.getId());
        return RespBody.success(voList);
    }

    @GetMapping("/couponScope")
    @ApiOperation("优惠券匹配的商品列表")
    public RespBody<List<ItemListVO>> couponScope(@Validated ItemCouponQueryDTO dto) {
        List<ItemListVO> voList = itemService.getCouponScopeByPage(dto);
        return RespBody.success(voList);
    }
}
