package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.RestaurantVoucherMapper;
import com.eghm.dao.model.RestaurantVoucher;
import com.eghm.model.dto.business.restaurant.voucher.RestaurantVoucherAddRequest;
import com.eghm.model.dto.business.restaurant.voucher.RestaurantVoucherEditRequest;
import com.eghm.model.dto.business.restaurant.voucher.RestaurantVoucherQueryRequest;
import com.eghm.service.business.RestaurantVoucherService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @date 2022/6/30
 */
@Service("restaurantVoucherService")
@AllArgsConstructor
@Slf4j
public class RestaurantVoucherServiceImpl implements RestaurantVoucherService {

    private final RestaurantVoucherMapper restaurantVoucherMapper;

    @Override
    public Page<RestaurantVoucher> getByPage(RestaurantVoucherQueryRequest request) {
        LambdaQueryWrapper<RestaurantVoucher> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getRestaurantId() != null, RestaurantVoucher::getRestaurantId, request.getRestaurantId());
        wrapper.eq(request.getState() != null, RestaurantVoucher::getState, request.getState());
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), RestaurantVoucher::getTitle, request.getQueryName());
        return restaurantVoucherMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(RestaurantVoucherAddRequest request) {
        RestaurantVoucher voucher = DataUtil.copy(request, RestaurantVoucher.class);
        voucher.setTotalNum(request.getVirtualNum());
        restaurantVoucherMapper.insert(voucher);
    }

    @Override
    public void update(RestaurantVoucherEditRequest request) {
        RestaurantVoucher select = restaurantVoucherMapper.selectById(request.getId());
        RestaurantVoucher voucher = DataUtil.copy(request, RestaurantVoucher.class);
        // 总销量要根据真实销量计算
        voucher.setTotalNum(request.getVirtualNum() + select.getSaleNum());
        restaurantVoucherMapper.updateById(voucher);
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<RestaurantVoucher> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(RestaurantVoucher::getId, id);
        wrapper.set(RestaurantVoucher::getState, state);
        restaurantVoucherMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, PlatformState state) {
        LambdaUpdateWrapper<RestaurantVoucher> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(RestaurantVoucher::getId, id);
        wrapper.set(RestaurantVoucher::getPlatformState, state);
        restaurantVoucherMapper.update(null, wrapper);
    }

    @Override
    public RestaurantVoucher selectById(Long id) {
        return restaurantVoucherMapper.selectById(id);
    }

    @Override
    public RestaurantVoucher selectByIdRequired(Long id) {
        RestaurantVoucher voucher = this.selectById(id);
        if (voucher == null) {
            log.error("餐饮券信息不存在 [{}]", id);
            throw new BusinessException(ErrorCode.VOUCHER_DOWN);
        }
        return voucher;
    }

    @Override
    public RestaurantVoucher selectByIdShelve(Long id) {
        RestaurantVoucher voucher = this.selectByIdRequired(id);
        if (voucher.getPlatformState() != PlatformState.SHELVE) {
            log.error("餐饮券未上架 [{}] [{}]", id, voucher.getPlatformState());
            throw new BusinessException(ErrorCode.VOUCHER_DOWN);
        }
        return voucher;
    }
}
