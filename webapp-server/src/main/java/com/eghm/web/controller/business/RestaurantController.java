package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.restaurant.RestaurantQueryDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.vo.business.restaurant.RestaurantListVO;
import com.eghm.vo.business.restaurant.RestaurantVO;
import com.eghm.service.business.RestaurantService;
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
 * @date 2022/6/30
 */
@RestController
@Api(tags = "餐饮门店")
@AllArgsConstructor
@RequestMapping("/webapp/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/listPage")
    @ApiOperation("商家列表")
    public RespBody<List<RestaurantListVO>> listPage(RestaurantQueryDTO dto) {
        List<RestaurantListVO> byPage = restaurantService.getByPage(dto);
        return RespBody.success(byPage);
    }

    @GetMapping("/detail")
    @ApiOperation("店铺详情")
    public RespBody<RestaurantVO> detail(@Validated IdDTO dto) {
        RestaurantVO detail = restaurantService.detailById(dto.getId());
        return RespBody.success(detail);
    }
}
