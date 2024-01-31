package com.eghm.service.business.impl;

import com.eghm.mapper.VenueMapper;
import com.eghm.service.business.VenueService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 场馆信息 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */
@Slf4j
@AllArgsConstructor
@Service("venueService")
public class VenueServiceImpl implements VenueService {

    private final VenueMapper venueMapper;
}
