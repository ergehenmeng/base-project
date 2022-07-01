package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.RestaurantVoucher;
import com.eghm.model.dto.IdStateDTO;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.business.restaurant.voucher.RestaurantVoucherAddRequest;
import com.eghm.model.dto.business.restaurant.voucher.RestaurantVoucherEditRequest;
import com.eghm.model.dto.business.restaurant.voucher.RestaurantVoucherQueryRequest;
import com.eghm.service.business.RestaurantVoucherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @date 2022/6/30
 */
@RestController
@Api(tags = "餐饮券")
@AllArgsConstructor
@RequestMapping("/restaurant/voucher")
public class RestaurantVoucherController {

    private final RestaurantVoucherService restaurantVoucherService;

    @GetMapping("/listPage")
    @ApiOperation("餐饮券列表")
    public PageData<RestaurantVoucher> listPage(RestaurantVoucherQueryRequest request) {
        Page<RestaurantVoucher> roomPage = restaurantVoucherService.getByPage(request);
        return PageData.toPage(roomPage);
    }

    @PostMapping("/create")
    @ApiOperation("新增餐饮券")
    public RespBody<Void> create(@Validated @RequestBody RestaurantVoucherAddRequest request) {
        restaurantVoucherService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新餐饮券")
    public RespBody<Void> update(@Validated @RequestBody RestaurantVoucherEditRequest request) {
        restaurantVoucherService.update(request);
        return RespBody.success();
    }

    @PostMapping("/updateState")
    @ApiOperation("更新上下架状态")
    public RespBody<Void> updateState(@Validated @RequestBody IdStateDTO dto) {
        restaurantVoucherService.updateState(dto.getId(), dto.getState());
        return RespBody.success();
    }
}
