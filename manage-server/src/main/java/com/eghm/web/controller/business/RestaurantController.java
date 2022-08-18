package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.dao.model.Restaurant;
import com.eghm.model.dto.IdDTO;
import com.eghm.model.dto.business.restaurant.RestaurantAddRequest;
import com.eghm.model.dto.business.restaurant.RestaurantEditRequest;
import com.eghm.model.dto.business.restaurant.RestaurantQueryRequest;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.business.RestaurantService;
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
@Api(tags = "餐饮商家")
@AllArgsConstructor
@RequestMapping("/manage/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/listPage")
    @ApiOperation("商家列表")
    public PageData<Restaurant> listPage(RestaurantQueryRequest request) {
        Page<Restaurant> roomPage = restaurantService.getByPage(request);
        return PageData.toPage(roomPage);
    }

    @PostMapping("/create")
    @ApiOperation("新增商家")
    public RespBody<Void> create(@Validated @RequestBody RestaurantAddRequest request) {
        restaurantService.create(request);
        return RespBody.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新商家")
    public RespBody<Void> update(@Validated @RequestBody RestaurantEditRequest request) {
        restaurantService.update(request);
        return RespBody.success();
    }

    @PostMapping("/shelves")
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        restaurantService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/unShelves")
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        restaurantService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformAudit")
    @ApiOperation("平台上架审核")
    public RespBody<Void> updateState(@RequestBody @Validated IdDTO dto) {
        restaurantService.updateAuditState(dto.getId(), PlatformState.SHELVE);
        return RespBody.success();
    }

    @PostMapping("/platformUnShelves")
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        restaurantService.updateAuditState(dto.getId(), PlatformState.UN_SHELVE);
        return RespBody.success();
    }
}
