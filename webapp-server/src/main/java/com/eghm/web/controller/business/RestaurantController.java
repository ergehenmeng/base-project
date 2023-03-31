package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.restaurant.RestaurantQueryDTO;
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
@Api(tags = "餐饮店")
@AllArgsConstructor
@RequestMapping("/webapp/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/listPage")
    @ApiOperation("商家列表")
    public List<RestaurantListVO> listPage(RestaurantQueryDTO dto) {
        return restaurantService.getByPage(dto);
    }

    @GetMapping("/detail")
    @ApiOperation("店铺详情")
    public RestaurantVO detail(@Validated IdDTO dto) {
        return restaurantService.detailById(dto.getId());
    }
}
