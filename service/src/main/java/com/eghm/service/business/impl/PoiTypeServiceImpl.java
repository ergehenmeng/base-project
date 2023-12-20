package com.eghm.service.business.impl;

import com.eghm.mapper.PoiTypeMapper;
import com.eghm.service.business.PoiTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * poi类型表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-20
 */
@Slf4j
@AllArgsConstructor
@Service("poiTypeService")
public class PoiTypeServiceImpl implements PoiTypeService {

    private final PoiTypeMapper poiTypeMapper;
}
