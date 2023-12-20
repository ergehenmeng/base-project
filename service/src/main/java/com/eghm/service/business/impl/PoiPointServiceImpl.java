package com.eghm.service.business.impl;

import com.eghm.mapper.PoiPointMapper;
import com.eghm.service.business.PoiPointService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * poi点位信息 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-20
 */
@Slf4j
@AllArgsConstructor
@Service("poiPointService")
public class PoiPointServiceImpl implements PoiPointService {

    private final PoiPointMapper poiPointMapper;
}
