package com.eghm.web.controller.business;

import cn.hutool.core.collection.CollUtil;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.item.ItemCouponQueryDTO;
import com.eghm.dto.business.item.ItemQueryDTO;
import com.eghm.dto.business.item.express.ExpressFeeCalcDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ErrorCode;
import com.eghm.service.business.CouponService;
import com.eghm.service.business.ItemService;
import com.eghm.vo.business.coupon.CouponListVO;
import com.eghm.vo.business.item.ItemDetailVO;
import com.eghm.vo.business.item.ItemVO;
import com.eghm.vo.business.item.express.TotalExpressVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/1/23
 */

@RestController
@Api(tags = "商品信息")
@AllArgsConstructor
@RequestMapping(value = "/webapp/item", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {

    private final ItemService itemService;

    private final CouponService couponService;

    @GetMapping("/listPage")
    @ApiOperation("商品列表")
    public RespBody<List<ItemVO>> listPage(ItemQueryDTO dto) {
        List<ItemVO> byPage = itemService.getByPage(dto);
        return RespBody.success(byPage);
    }

    @GetMapping("/detail")
    @ApiOperation("商品详情")
    public RespBody<ItemDetailVO> detail(@Validated IdDTO dto) {
        ItemDetailVO detail = itemService.detailById(dto.getId());
        return RespBody.success(detail);
    }

    @GetMapping("/recommend")
    @ApiOperation("商品推荐列表")
    public RespBody<List<ItemVO>> recommend() {
        List<ItemVO> recommend = itemService.getRecommend();
        return RespBody.success(recommend);
    }

    @PostMapping(value = "/calcExpress", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("计算快递费")
    public RespBody<TotalExpressVO> calcExpress(@RequestBody @Validated List<ExpressFeeCalcDTO> dtoList) {
        if (CollUtil.isEmpty(dtoList)) {
            return RespBody.error(ErrorCode.ORDER_ITEM_NULL);
        }
        TotalExpressVO expressFee = itemService.calcExpressFee(dtoList);
        return RespBody.success(expressFee);
    }

    @GetMapping("/couponList")
    @ApiOperation("商品页可以领取的优惠券")
    @ApiImplicitParam(name = "itemId", value = "商品id", required = true)
    public RespBody<List<CouponListVO>> couponList(@RequestParam("itemId") Long itemId) {
        List<CouponListVO> voList = couponService.getItemCoupon(itemId);
        return RespBody.success(voList);
    }

    @GetMapping("/couponScope")
    @ApiOperation("优惠券匹配的商品列表")
    public RespBody<List<ItemVO>> couponScope(@Validated ItemCouponQueryDTO dto) {
        List<ItemVO> voList = itemService.getCouponScopeByPage(dto);
        return RespBody.success(voList);
    }

}
