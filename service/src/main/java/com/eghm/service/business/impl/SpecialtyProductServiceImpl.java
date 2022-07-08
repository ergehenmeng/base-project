package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ref.AuditState;
import com.eghm.common.enums.ref.State;
import com.eghm.dao.mapper.SpecialtyProductMapper;
import com.eghm.dao.model.SpecialtyProduct;
import com.eghm.model.dto.business.specialty.product.SpecialtyProductAddRequest;
import com.eghm.model.dto.business.specialty.product.SpecialtyProductEditRequest;
import com.eghm.service.business.SpecialtyProductService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
@Service("specialtyProductService")
@AllArgsConstructor
public class SpecialtyProductServiceImpl implements SpecialtyProductService {

    private final SpecialtyProductMapper specialtyProductMapper;

    @Override
    public void create(SpecialtyProductAddRequest request) {
        // TODO 商户id
        SpecialtyProduct product = DataUtil.copy(request, SpecialtyProduct.class);
        specialtyProductMapper.insert(product);
    }

    @Override
    public void update(SpecialtyProductEditRequest request) {
        SpecialtyProduct product = DataUtil.copy(request, SpecialtyProduct.class);
        specialtyProductMapper.updateById(product);
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<SpecialtyProduct> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SpecialtyProduct::getId, id);
        wrapper.set(SpecialtyProduct::getState, state);
        specialtyProductMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, AuditState state) {
        LambdaUpdateWrapper<SpecialtyProduct> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SpecialtyProduct::getId, id);
        wrapper.set(SpecialtyProduct::getAuditState, state);
        specialtyProductMapper.update(null, wrapper);
    }
}
