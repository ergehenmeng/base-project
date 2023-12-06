package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.restaurant.voucher.MealVoucherAddRequest;
import com.eghm.dto.business.restaurant.voucher.MealVoucherEditRequest;
import com.eghm.dto.business.restaurant.voucher.VoucherQueryDTO;
import com.eghm.dto.business.restaurant.voucher.VoucherQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.State;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.MealVoucherMapper;
import com.eghm.model.MealVoucher;
import com.eghm.service.business.MealVoucherService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.restaurant.VoucherDetailVO;
import com.eghm.vo.business.restaurant.VoucherResponse;
import com.eghm.vo.business.restaurant.VoucherVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/6/30
 */
@Service("restaurantVoucherService")
@AllArgsConstructor
@Slf4j
public class MealVoucherServiceImpl implements MealVoucherService {

    private final MealVoucherMapper mealVoucherMapper;

    @Override
    public Page<MealVoucher> getByPage(VoucherQueryRequest request) {
        LambdaQueryWrapper<MealVoucher> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getMerchantId() != null, MealVoucher::getMerchantId, request.getRestaurantId());
        wrapper.eq(request.getRestaurantId() != null, MealVoucher::getRestaurantId, request.getRestaurantId());
        wrapper.eq(request.getState() != null, MealVoucher::getState, request.getState());
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), MealVoucher::getTitle, request.getQueryName());
        return mealVoucherMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public List<VoucherResponse> getList(VoucherQueryRequest request) {
        Page<VoucherResponse> listPage = mealVoucherMapper.listPage(request.createPage(false), request);
        return listPage.getRecords();
    }

    @Override
    public void create(MealVoucherAddRequest request) {
        this.redoTitle(request.getTitle(), null, request.getRestaurantId());
        MealVoucher voucher = DataUtil.copy(request, MealVoucher.class);
        voucher.setTotalNum(request.getVirtualNum());
        voucher.setMerchantId(SecurityHolder.getMerchantId());
        mealVoucherMapper.insert(voucher);
    }

    @Override
    public void update(MealVoucherEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId(), request.getRestaurantId());
        
        MealVoucher select = mealVoucherMapper.selectById(request.getId());
        MealVoucher voucher = DataUtil.copy(request, MealVoucher.class);
        // 总销量要根据真实销量计算
        voucher.setTotalNum(request.getVirtualNum() + select.getSaleNum());
        mealVoucherMapper.updateById(voucher);
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<MealVoucher> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MealVoucher::getId, id);
        wrapper.set(MealVoucher::getState, state);
        mealVoucherMapper.update(null, wrapper);
    }

    @Override
    public MealVoucher selectById(Long id) {
        return mealVoucherMapper.selectById(id);
    }

    @Override
    public MealVoucher selectByIdRequired(Long id) {
        MealVoucher voucher = this.selectById(id);
        if (voucher == null) {
            log.error("餐饮券信息不存在 [{}]", id);
            throw new BusinessException(ErrorCode.VOUCHER_DOWN);
        }
        return voucher;
    }

    @Override
    public MealVoucher selectByIdShelve(Long id) {
        MealVoucher voucher = this.selectByIdRequired(id);
        if (voucher.getState() != State.SHELVE) {
            log.error("餐饮券未上架 [{}] [{}]", id, voucher.getState());
            throw new BusinessException(ErrorCode.VOUCHER_DOWN);
        }
        return voucher;
    }

    @Override
    public void updateStock(Long id, Integer num) {
        int stock = mealVoucherMapper.updateStock(id, num);
        if (stock != 1) {
            log.error("餐饮券更新库存失败 [{}] [{}] [{}]", id, num, stock);
            throw new BusinessException(ErrorCode.VOUCHER_STOCK);
        }
    }

    @Override
    public void deleteById(Long id) {
        mealVoucherMapper.deleteById(id);
    }

    @Override
    public List<VoucherVO> getByPage(VoucherQueryDTO dto) {
        Page<VoucherVO> voPage = mealVoucherMapper.getList(dto.createPage(false), dto);
        return voPage.getRecords();
    }

    @Override
    public VoucherDetailVO getDetail(Long id) {
        MealVoucher voucher = this.selectByIdShelve(id);
        return DataUtil.copy(voucher, VoucherDetailVO.class);
    }

    /**
     * 针对同一个家店铺餐饮区名称不能重复
     * @param title 餐券名称
     * @param id 餐券id 编辑时不能为空
     * @param restaurantId 所属餐厅
     */
    public void redoTitle(String title, Long id, Long restaurantId) {
        LambdaQueryWrapper<MealVoucher> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MealVoucher::getTitle, title);
        wrapper.ne(id != null, MealVoucher::getId, id);
        wrapper.eq(MealVoucher::getRestaurantId, restaurantId);
        Long count = mealVoucherMapper.selectCount(wrapper);
        if (count > 0) {
            log.info("餐饮券名称重复 [{}] [{}] [{}]", title, id, restaurantId);
            throw new BusinessException(ErrorCode.VOUCHER_TITLE_REDO);
        }
    }
}
