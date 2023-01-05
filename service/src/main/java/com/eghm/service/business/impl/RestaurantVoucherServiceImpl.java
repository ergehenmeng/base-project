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
import com.eghm.mapper.RestaurantVoucherMapper;
import com.eghm.model.RestaurantVoucher;
import com.eghm.model.dto.business.restaurant.voucher.RestaurantVoucherAddRequest;
import com.eghm.model.dto.business.restaurant.voucher.RestaurantVoucherEditRequest;
import com.eghm.model.dto.business.restaurant.voucher.RestaurantVoucherQueryRequest;
import com.eghm.service.business.RestaurantVoucherService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
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
        this.redoTitle(request.getTitle(), null, request.getRestaurantId());
        RestaurantVoucher voucher = DataUtil.copy(request, RestaurantVoucher.class);
        voucher.setTotalNum(request.getVirtualNum());
        restaurantVoucherMapper.insert(voucher);
    }

    @Override
    public void update(RestaurantVoucherEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId(), request.getRestaurantId());
        
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

    @Override
    public void updateStock(Long id, Integer num) {
        int stock = restaurantVoucherMapper.updateStock(id, num);
        if (stock != 1) {
            log.error("餐饮券更新库存失败 [{}] [{}] [{}]", id, num, stock);
            throw new BusinessException(ErrorCode.VOUCHER_STOCK);
        }
    }

    @Override
    public void deleteById(Long id) {
        restaurantVoucherMapper.deleteById(id);
    }

    /**
     * 针对同一个家店铺餐饮区名称不能重复
     * @param title 餐券名称
     * @param id 餐券id 编辑时不能为空
     * @param restaurantId 所属餐厅
     */
    public void redoTitle(String title, Long id, Long restaurantId) {
        LambdaQueryWrapper<RestaurantVoucher> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(RestaurantVoucher::getTitle, title);
        wrapper.ne(id != null, RestaurantVoucher::getId, id);
        wrapper.eq(RestaurantVoucher::getRestaurantId, restaurantId);
        Integer count = restaurantVoucherMapper.selectCount(wrapper);
        if (count > 0) {
            log.info("餐饮券名称重复 [{}] [{}] [{}]", title, id, restaurantId);
            throw new BusinessException(ErrorCode.VOUCHER_TITLE_REDO);
        }
    }
}
