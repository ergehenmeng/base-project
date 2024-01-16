package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.restaurant.voucher.VoucherAddRequest;
import com.eghm.dto.business.restaurant.voucher.VoucherEditRequest;
import com.eghm.dto.business.restaurant.voucher.VoucherQueryDTO;
import com.eghm.dto.business.restaurant.voucher.VoucherQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.State;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.VoucherMapper;
import com.eghm.model.Voucher;
import com.eghm.service.business.VoucherService;
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
@Service("voucherService")
@AllArgsConstructor
@Slf4j
public class VoucherServiceImpl implements VoucherService {

    private final VoucherMapper voucherMapper;

    @Override
    public Page<Voucher> getByPage(VoucherQueryRequest request) {
        LambdaQueryWrapper<Voucher> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getMerchantId() != null, Voucher::getMerchantId, request.getRestaurantId());
        wrapper.eq(request.getRestaurantId() != null, Voucher::getRestaurantId, request.getRestaurantId());
        wrapper.eq(request.getState() != null, Voucher::getState, request.getState());
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), Voucher::getTitle, request.getQueryName());
        return voucherMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public List<VoucherResponse> getList(VoucherQueryRequest request) {
        Page<VoucherResponse> listPage = voucherMapper.listPage(request.createPage(false), request);
        return listPage.getRecords();
    }

    @Override
    public void create(VoucherAddRequest request) {
        this.redoTitle(request.getTitle(), null, request.getRestaurantId());
        Voucher voucher = DataUtil.copy(request, Voucher.class);
        voucher.setTotalNum(request.getVirtualNum());
        voucher.setMerchantId(SecurityHolder.getMerchantId());
        voucherMapper.insert(voucher);
    }

    @Override
    public void update(VoucherEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId(), request.getRestaurantId());

        Voucher select = voucherMapper.selectById(request.getId());
        Voucher voucher = DataUtil.copy(request, Voucher.class);
        // 总销量要根据真实销量计算
        voucher.setTotalNum(request.getVirtualNum() + select.getSaleNum());
        voucherMapper.updateById(voucher);
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<Voucher> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Voucher::getId, id);
        wrapper.set(Voucher::getState, state);
        voucherMapper.update(null, wrapper);
    }

    @Override
    public Voucher selectById(Long id) {
        return voucherMapper.selectById(id);
    }

    @Override
    public Voucher selectByIdRequired(Long id) {
        Voucher voucher = this.selectById(id);
        if (voucher == null) {
            log.error("餐饮券信息不存在 [{}]", id);
            throw new BusinessException(ErrorCode.VOUCHER_DOWN);
        }
        return voucher;
    }

    @Override
    public Voucher selectByIdShelve(Long id) {
        Voucher voucher = this.selectByIdRequired(id);
        if (voucher.getState() != State.SHELVE) {
            log.error("餐饮券未上架 [{}] [{}]", id, voucher.getState());
            throw new BusinessException(ErrorCode.VOUCHER_DOWN);
        }
        return voucher;
    }

    @Override
    public void updateStock(Long id, Integer num) {
        int stock = voucherMapper.updateStock(id, num);
        if (stock != 1) {
            log.error("餐饮券更新库存失败 [{}] [{}] [{}]", id, num, stock);
            throw new BusinessException(ErrorCode.VOUCHER_STOCK);
        }
    }

    @Override
    public void deleteById(Long id) {
        LambdaUpdateWrapper<Voucher> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Voucher::getId, id);
        wrapper.set(Voucher::getState, State.UN_SHELVE);
        wrapper.set(Voucher::getDeleted, true);
        voucherMapper.update(null, wrapper);
    }

    @Override
    public List<VoucherVO> getByPage(VoucherQueryDTO dto) {
        Page<VoucherVO> voPage = voucherMapper.getList(dto.createPage(false), dto);
        return voPage.getRecords();
    }

    @Override
    public VoucherDetailVO getDetail(Long id) {
        Voucher voucher = this.selectByIdShelve(id);
        return DataUtil.copy(voucher, VoucherDetailVO.class);
    }

    /**
     * 针对同一个家店铺餐饮区名称不能重复
     *
     * @param title        餐券名称
     * @param id           餐券id 编辑时不能为空
     * @param restaurantId 所属餐厅
     */
    public void redoTitle(String title, Long id, Long restaurantId) {
        LambdaQueryWrapper<Voucher> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Voucher::getTitle, title);
        wrapper.ne(id != null, Voucher::getId, id);
        wrapper.eq(Voucher::getRestaurantId, restaurantId);
        Long count = voucherMapper.selectCount(wrapper);
        if (count > 0) {
            log.info("餐饮券名称重复 [{}] [{}] [{}]", title, id, restaurantId);
            throw new BusinessException(ErrorCode.VOUCHER_TITLE_REDO);
        }
    }
}
