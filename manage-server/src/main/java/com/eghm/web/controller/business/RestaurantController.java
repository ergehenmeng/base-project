package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.restaurant.RestaurantAddRequest;
import com.eghm.dto.business.restaurant.RestaurantEditRequest;
import com.eghm.dto.business.restaurant.RestaurantQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.ref.State;
import com.eghm.model.Restaurant;
import com.eghm.service.business.RestaurantService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.restaurant.RestaurantDetailResponse;
import com.eghm.vo.business.restaurant.RestaurantResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/6/30
 */
@RestController
@Api(tags = "餐饮商家")
@AllArgsConstructor
@RequestMapping(value = "/manage/restaurant", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<RestaurantResponse>> listPage(RestaurantQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<RestaurantResponse> roomPage = restaurantService.getByPage(request);
        return RespBody.success(PageData.toPage(roomPage));
    }

    @GetMapping("/storeListPage")
    @ApiOperation("列表含商户信息")
    public RespBody<PageData<BaseStoreResponse>> storeListPage(BaseStoreQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        Page<BaseStoreResponse> listPage = restaurantService.getStorePage(request);
        return RespBody.success(PageData.toPage(listPage));
    }


    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody RestaurantAddRequest request) {
        restaurantService.create(request);
        return RespBody.success();
    }

    /**
     * 对于注册的商户来说, 首次编辑商家信息即为开通商户
     */
    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("编辑")
    public RespBody<Void> update(@Validated @RequestBody RestaurantEditRequest request) {
        restaurantService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/shelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("上架")
    public RespBody<Void> shelves(@Validated @RequestBody IdDTO dto) {
        restaurantService.updateState(dto.getId(), State.SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/unShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("下架")
    public RespBody<Void> unShelves(@Validated @RequestBody IdDTO dto) {
        restaurantService.updateState(dto.getId(), State.UN_SHELVE);
        return RespBody.success();
    }

    @PostMapping(value = "/platformUnShelves", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("平台下架")
    public RespBody<Void> platformUnShelves(@RequestBody @Validated IdDTO dto) {
        restaurantService.updateState(dto.getId(), State.FORCE_UN_SHELVE);
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("详情")
    public RespBody<RestaurantDetailResponse> select(@Validated IdDTO dto) {
        Restaurant restaurant = restaurantService.selectByIdRequired(dto.getId());
        return RespBody.success(DataUtil.copy(restaurant, RestaurantDetailResponse.class));
    }

    /**
     * 注意: 非自营商品删除需要慎重,店铺关联的商户或者订单等等需要额外考虑
     */
    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        restaurantService.deleteById(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(HttpServletResponse response, RestaurantQueryRequest request) {
        request.setMerchantId(SecurityHolder.getMerchantId());
        List<RestaurantResponse> byPage = restaurantService.getList(request);
        EasyExcelUtil.export(response, "餐饮店铺", byPage, RestaurantResponse.class);
    }
}
