package com.eghm.service.business.impl;

import com.eghm.dao.mapper.SpecialtyProductSkuMapper;
import com.eghm.service.business.SpecialtyProductSkuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
@Service("specialtyProductSkuService")
@AllArgsConstructor
public class SpecialtyProductSkuServiceImpl implements SpecialtyProductSkuService {

    private final SpecialtyProductSkuMapper specialtyProductSkuMapper;

}
