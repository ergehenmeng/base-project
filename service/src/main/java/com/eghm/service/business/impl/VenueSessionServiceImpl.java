package com.eghm.service.business.impl;

import com.eghm.mapper.VenueSessionMapper;
import com.eghm.service.business.VenueSessionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 场馆场次价格信息表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */
@Slf4j
@AllArgsConstructor
@Service("venueSessionService")
public class VenueSessionServiceImpl implements VenueSessionService {

    private final VenueSessionMapper venueSessionMapper;
}
