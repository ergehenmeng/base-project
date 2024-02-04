package com.eghm.service.business.impl;

import com.eghm.mapper.VenueOrderMapper;
import com.eghm.service.business.VenueOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 场馆预约订单表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-04
 */
@Slf4j
@AllArgsConstructor
@Service("venueOrderService")
public class VenueOrderServiceImpl implements VenueOrderService {

    private final VenueOrderMapper venueOrderMapper;
}
