package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.enums.ref.PlatformState;
import com.eghm.enums.ref.State;
import com.eghm.model.Restaurant;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.restaurant.RestaurantAddRequest;
import com.eghm.dto.business.restaurant.RestaurantEditRequest;
import com.eghm.dto.business.restaurant.RestaurantQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.RestaurantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    
    private final CommonService commonService;

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
    
    /**
     * 对于注册的商户来说, 首次编辑商家信息即为开通商户
     */
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

    @GetMapping("/select")
    @ApiOperation("详情")
    public RespBody<Restaurant> select(@Validated IdDTO dto) {
        Restaurant restaurant = restaurantService.selectByIdRequired(dto.getId());
        commonService.checkIllegal(restaurant.getMerchantId());
        return RespBody.success(restaurant);
    }
    
    /**
     * 注意: 非自营商品删除需要慎重,店铺关联的商户或者订单等等需要额外考虑
     */
    @GetMapping("/delete")
    @ApiOperation("删除")
    public RespBody<Restaurant> delete(@RequestBody @Validated IdDTO dto) {
        restaurantService.deleteById(dto.getId());
        return RespBody.success();
    }
    
}
