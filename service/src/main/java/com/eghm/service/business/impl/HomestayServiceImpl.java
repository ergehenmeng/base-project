package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.AuditState;
import com.eghm.common.enums.ref.State;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.HomestayMapper;
import com.eghm.dao.model.Homestay;
import com.eghm.model.dto.business.homestay.HomestayAddRequest;
import com.eghm.model.dto.business.homestay.HomestayEditRequest;
import com.eghm.model.dto.business.homestay.HomestayQueryRequest;
import com.eghm.service.business.HomestayService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛 2022/6/25 14:09
 */
@Service("homestayService")
@AllArgsConstructor
@Slf4j
public class HomestayServiceImpl implements HomestayService {

    private final HomestayMapper homestayMapper;

    @Override
    public Page<Homestay> getByPage(HomestayQueryRequest request) {
        LambdaQueryWrapper<Homestay> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getState() != null, Homestay::getState, request.getState());
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), Homestay::getTitle, request.getQueryName());
        return homestayMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(HomestayAddRequest request) {
        this.checkTitleRedo(request.getTitle(), null);
        // TODO 商家id补充
        Homestay homestay = DataUtil.copy(request, Homestay.class);
        homestayMapper.insert(homestay);
    }

    @Override
    public void update(HomestayEditRequest request) {
        this.checkTitleRedo(request.getTitle(), request.getId());
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<Homestay> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Homestay::getId, id);
        wrapper.set(Homestay::getState, state);
        homestayMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, AuditState state) {
        LambdaUpdateWrapper<Homestay> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Homestay::getId, id);
        wrapper.set(Homestay::getAuditState, state);
        homestayMapper.update(null, wrapper);
    }

    /**
     * 校验民宿名称是否被占用
     * @param title 民宿名称
     * @param id    id
     */
    private void checkTitleRedo(String title, Long id) {
        LambdaQueryWrapper<Homestay> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Homestay::getTitle, title);
        wrapper.ne(id != null, Homestay::getId, id);
        Integer count = homestayMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("民宿名称被占用 [{}]", title);
            throw new BusinessException(ErrorCode.HOMESTAY_TITLE_REDO);
        }
    }

}
