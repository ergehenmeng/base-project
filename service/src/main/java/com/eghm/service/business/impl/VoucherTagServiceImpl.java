package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.VoucherTagAddRequest;
import com.eghm.dto.business.VoucherTagEditRequest;
import com.eghm.dto.business.VoucherTagQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.VoucherTagMapper;
import com.eghm.model.VoucherTag;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.VoucherTagService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.restaurant.TagSelectResponse;
import com.eghm.vo.business.restaurant.VoucherTagResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 二哥很猛
* @since 2024-10-09
*/
@Slf4j
@Service("voucherTagService")
@AllArgsConstructor
public class VoucherTagServiceImpl implements VoucherTagService {

    private final CommonService commonService;

    private final VoucherTagMapper voucherTagMapper;

    @Override
    public Page<VoucherTagResponse> getByPage(VoucherTagQueryRequest request) {
        return voucherTagMapper.getByPage(request.createPage(), request);
    }

    @Override
    public List<TagSelectResponse> getList(Long restaurantId) {
        return voucherTagMapper.getList(restaurantId);
    }

    @Override
    public void create(VoucherTagAddRequest request) {
        this.redoTitle(request.getTitle(), null);
        VoucherTag data = DataUtil.copy(request, VoucherTag.class);
        data.setMerchantId(SecurityHolder.getMerchantId());
        voucherTagMapper.insert(data);
    }

    @Override
    public void update(VoucherTagEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId());
        VoucherTag select = voucherTagMapper.selectById(request.getId());
        commonService.checkIllegal(select.getMerchantId());

        VoucherTag data = DataUtil.copy(request, VoucherTag.class);
        voucherTagMapper.updateById(data);
    }


    @Override
    public void sortBy(Long id, Integer sortBy) {
        LambdaUpdateWrapper<VoucherTag> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(VoucherTag::getId, id);
        wrapper.set(VoucherTag::getSort, sortBy);
        voucherTagMapper.update(null, wrapper);
    }

    @Override
    public void delete(Long id) {
        LambdaUpdateWrapper<VoucherTag> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(VoucherTag::getId, id);
        wrapper.set(VoucherTag::getDeleted, true);
        wrapper.eq(VoucherTag::getMerchantId, SecurityHolder.getMerchantId());
        voucherTagMapper.update(null, wrapper);
    }

    /**
    * 校验名称是否重复
    *
    * @param title 名称
    * @param id  编辑时不能为空
    */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<VoucherTag> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(VoucherTag::getTitle, title);
        wrapper.ne(id != null, VoucherTag::getId, id);
        Long count = voucherTagMapper.selectCount(wrapper);
        if (count > 0) {
            log.info("标签名称重复 [{}] [{}]", title, id);
            throw new BusinessException(ErrorCode.VOUCHER_TAG_REDO);
        }
    }

}