package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.model.RestaurantVoucher;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.business.restaurant.voucher.RestaurantVoucherAddRequest;
import com.eghm.model.dto.business.restaurant.voucher.RestaurantVoucherEditRequest;
import com.eghm.model.dto.business.restaurant.voucher.RestaurantVoucherQueryRequest;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
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
@RequestMapping("/manage/restaurant/voucher")
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

    @PostMapping("/shelves")
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        restaurantVoucherService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/unShelves")
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        restaurantVoucherService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformAudit")
    @ApiOperation("平台上架审核")
    public RespBody<Void> updateState(@RequestBody @Validated IdDTO dto) {
        restaurantVoucherService.updateAuditState(dto.getId(), PlatformState.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformUnShelves")
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        restaurantVoucherService.updateAuditState(dto.getId(), PlatformState.UN_SHELVE);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("餐饮券详情")
    public RespBody<RestaurantVoucher> select(IdDTO dto) {
        RestaurantVoucher voucher = restaurantVoucherService.selectByIdRequired(dto.getId());
        return RespBody.success(voucher);
    }

    @PostMapping("/delete")
    @ApiOperation("删除商品")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        restaurantVoucherService.deleteById(dto.getId());
        return RespBody.success();
    }

}
