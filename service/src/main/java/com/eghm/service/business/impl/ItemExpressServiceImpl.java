package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.item.express.ItemExpressAddRequest;
import com.eghm.dto.business.item.express.ItemExpressEditRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ItemExpressMapper;
import com.eghm.mapper.ItemMapper;
import com.eghm.model.Item;
import com.eghm.model.ItemExpress;
import com.eghm.service.business.ItemExpressRegionService;
import com.eghm.service.business.ItemExpressService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.item.express.ItemExpressResponse;
import com.eghm.vo.business.item.express.ItemExpressVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 快递模板表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-22
 */
@Slf4j
@AllArgsConstructor
@Service("itemExpressService")
public class ItemExpressServiceImpl implements ItemExpressService {

    private final ItemExpressMapper itemExpressMapper;

    private final ItemExpressRegionService itemExpressRegionService;

    private final ItemMapper itemMapper;

    @Override
    public List<ItemExpressResponse> getList(Long merchantId) {
        return itemExpressMapper.getList(merchantId);
    }

    @Override
    public void create(ItemExpressAddRequest request) {
        ItemExpress express = DataUtil.copy(request, ItemExpress.class);
        itemExpressMapper.insert(express);
        itemExpressRegionService.createOrUpdate(express.getId(), request.getRegionList());
    }

    @Override
    public void update(ItemExpressEditRequest request) {
        ItemExpress selected = itemExpressMapper.selectById(request.getId());
        if (selected == null || !selected.getMerchantId().equals(request.getMerchantId())) {
            log.error("查询快递模板不合法 [{}] [{}]", request.getId(), request.getMerchantId());
            throw new BusinessException(ErrorCode.EXPRESS_NULL);
        }
        if (!selected.getChargeMode().equals(request.getChargeMode()) && this.itemCount(request.getId()) > 0) {
            throw new BusinessException(ErrorCode.EXPRESS_CHARGE_MODE);
        }
        ItemExpress express = DataUtil.copy(request, ItemExpress.class);
        itemExpressMapper.updateById(express);
        itemExpressRegionService.createOrUpdate(express.getId(), request.getRegionList());
    }

    @Override
    public List<ItemExpressVO> getExpressList(List<Long> itemIds, Long storeId) {
        if (CollUtil.isEmpty(itemIds)) {
            return Lists.newArrayList();
        }
        return itemExpressMapper.getExpressList(itemIds, storeId);
    }

    @Override
    public void deleteById(Long id) {
        Long count = this.itemCount(id);
        if (count > 0) {
            throw new BusinessException(ErrorCode.EXPRESS_NOT_DELETE);
        }
        LambdaUpdateWrapper<ItemExpress> wrapper = Wrappers.lambdaUpdate();
        // 防止误删除其他人的信息
        wrapper.eq(ItemExpress::getMerchantId, SecurityHolder.getMerchantId());
        wrapper.eq(ItemExpress::getId, id);
        itemExpressMapper.delete(wrapper);
    }

    /**
     * 统计快递被零售商品占用的数量
     * @param expressId 快递id
     * @return 数量
     */
    private Long itemCount(Long expressId) {
        LambdaQueryWrapper<Item> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Item::getExpressId, expressId);
        return itemMapper.selectCount(wrapper);
    }
}
