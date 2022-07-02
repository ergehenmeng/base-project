package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.AuditState;
import com.eghm.common.enums.ref.State;
import com.eghm.dao.mapper.ScenicMapper;
import com.eghm.dao.model.HomestayRoom;
import com.eghm.dao.model.Scenic;
import com.eghm.model.dto.business.scenic.ScenicAddRequest;
import com.eghm.model.dto.business.scenic.ScenicEditRequest;
import com.eghm.model.dto.business.scenic.ScenicQueryRequest;
import com.eghm.service.business.ScenicService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/6/14 22:22
 */
@Service("scenicService")
@AllArgsConstructor
public class ScenicServiceImpl implements ScenicService {

    private final ScenicMapper scenicMapper;

    @Override
    public Page<Scenic> getByPage(ScenicQueryRequest request) {
        LambdaQueryWrapper<Scenic> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getMerchantId() != null, Scenic::getMerchantId, request.getMerchantId());
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), Scenic::getScenicName, request.getQueryName());
        wrapper.eq(request.getState() != null, Scenic::getState, request.getState());
        wrapper.last(" order by sort asc, id desc ");
        return scenicMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void createScenic(ScenicAddRequest request) {
        // TODO 商户id添加
        Scenic scenic = DataUtil.copy(request, Scenic.class);
        scenicMapper.insert(scenic);
    }

    @Override
    public void updateScenic(ScenicEditRequest request) {
        Scenic scenic = DataUtil.copy(request, Scenic.class);
        scenicMapper.updateById(scenic);
    }

    @Override
    public Scenic getByMerchantId(Long merchantId) {
        LambdaQueryWrapper<Scenic> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Scenic::getMerchantId, merchantId);
        return scenicMapper.selectOne(wrapper);
    }

    @Override
    public Scenic selectById(Long id) {
        return scenicMapper.selectById(id);
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<Scenic> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Scenic::getId, id);
        wrapper.set(Scenic::getState, state);
        scenicMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, AuditState state) {
        LambdaUpdateWrapper<Scenic> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Scenic::getId, id);
        wrapper.set(Scenic::getAuditState, state);
        scenicMapper.update(null, wrapper);
    }
}
