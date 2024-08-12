package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.restaurant.voucher.VoucherAddRequest;
import com.eghm.dto.business.restaurant.voucher.VoucherEditRequest;
import com.eghm.dto.business.restaurant.voucher.VoucherQueryDTO;
import com.eghm.dto.business.restaurant.voucher.VoucherQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.State;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.VoucherMapper;
import com.eghm.model.Voucher;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.VoucherService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DateUtil;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.restaurant.VoucherDetailVO;
import com.eghm.vo.business.restaurant.VoucherResponse;
import com.eghm.vo.business.restaurant.VoucherVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/6/30
 */
@Service("voucherService")
@AllArgsConstructor
@Slf4j
public class VoucherServiceImpl implements VoucherService {

    private final VoucherMapper voucherMapper;

    private final CommonService commonService;

    @Override
    public Page<VoucherResponse> getByPage(VoucherQueryRequest request) {
        return voucherMapper.listPage(request.createNullPage(), request);
    }

    @Override
    public List<VoucherResponse> getList(VoucherQueryRequest request) {
        Page<VoucherResponse> listPage = voucherMapper.listPage(request.createNullPage(), request);
        return listPage.getRecords();
    }

    @Override
    public void create(VoucherAddRequest request) {
        this.redoTitle(request.getTitle(), null, request.getRestaurantId());
        Voucher voucher = DataUtil.copy(request, Voucher.class);
        voucher.setTotalNum(request.getVirtualNum());
        voucher.setMerchantId(SecurityHolder.getMerchantId());
        voucher.setCreateDate(LocalDate.now());
        voucher.setCreateMonth(LocalDate.now().format(DateUtil.MIN_FORMAT));
        voucher.setCoverUrl(CollUtil.join(request.getCoverList(), CommonConstant.COMMA));
        voucherMapper.insert(voucher);
    }

    @Override
    public void update(VoucherEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId(), request.getRestaurantId());
        Voucher select = voucherMapper.selectById(request.getId());
        commonService.checkIllegal(select.getMerchantId());
        Voucher voucher = DataUtil.copy(request, Voucher.class);
        // 总销量要根据真实销量计算
        voucher.setTotalNum(request.getVirtualNum() + select.getSaleNum());
        voucher.setCoverUrl(CollUtil.join(request.getCoverList(), CommonConstant.COMMA));
        voucherMapper.updateById(voucher);
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<Voucher> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Voucher::getId, id);
        Long merchantId = SecurityHolder.getMerchantId();
        wrapper.eq(merchantId != null, Voucher::getMerchantId, merchantId);
        wrapper.set(Voucher::getState, state);
        voucherMapper.update(null, wrapper);
    }

    @Override
    public Voucher selectByIdRequired(Long id) {
        Voucher voucher = voucherMapper.selectById(id);
        if (voucher == null) {
            log.error("餐饮券信息不存在 [{}]", id);
            throw new BusinessException(ErrorCode.VOUCHER_NULL);
        }
        return voucher;
    }

    @Override
    public Voucher selectByIdShelve(Long id) {
        Voucher voucher = voucherMapper.selectById(id);
        if (voucher == null || voucher.getState() != State.SHELVE) {
            log.error("餐饮券未上架 [{}]", id);
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
        wrapper.eq(Voucher::getMerchantId, SecurityHolder.getMerchantId());
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

    @Override
    public Page<BaseProductResponse> getProductPage(BaseProductQueryRequest request) {
        return voucherMapper.getProductPage(Boolean.TRUE.equals(request.getLimit()) ? request.createPage() : request.createNullPage(), request);
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
