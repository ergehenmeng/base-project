package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ref.AuditState;
import com.eghm.common.enums.ref.State;
import com.eghm.dao.mapper.SpecialtyShopMapper;
import com.eghm.dao.model.HomestayRoom;
import com.eghm.dao.model.ScenicTicket;
import com.eghm.dao.model.SpecialtyShop;
import com.eghm.model.dto.business.specialty.SpecialtyShopAddRequest;
import com.eghm.model.dto.business.specialty.SpecialtyShopEditRequest;
import com.eghm.service.business.SpecialtyShopService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
@Service("specialtyShopService")
@AllArgsConstructor
public class SpecialtyShopServiceImpl implements SpecialtyShopService {

    private final SpecialtyShopMapper specialtyShopMapper;

    @Override
    public void create(SpecialtyShopAddRequest request) {
        // TODO 商户id增加
        SpecialtyShop shop = DataUtil.copy(request, SpecialtyShop.class);
        specialtyShopMapper.insert(shop);
    }

    @Override
    public void update(SpecialtyShopEditRequest request) {
        SpecialtyShop shop = DataUtil.copy(request, SpecialtyShop.class);
        specialtyShopMapper.updateById(shop);
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<SpecialtyShop> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SpecialtyShop::getId, id);
        wrapper.set(SpecialtyShop::getState, state);
        specialtyShopMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, AuditState state) {
        LambdaUpdateWrapper<SpecialtyShop> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SpecialtyShop::getId, id);
        wrapper.set(SpecialtyShop::getAuditState, state);
        specialtyShopMapper.update(null, wrapper);
    }
}
