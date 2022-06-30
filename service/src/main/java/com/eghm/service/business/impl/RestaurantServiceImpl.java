package com.eghm.service.business.impl;

import com.eghm.dao.mapper.RestaurantMapper;
import com.eghm.service.business.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/6/30
 */
@Service("restaurantService")
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantMapper restaurantMapper;

}
