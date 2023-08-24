package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.dto.business.item.express.ItemExpressAddRequest;
import com.eghm.dto.business.item.express.ItemExpressEditRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ItemExpressMapper;
import com.eghm.model.ItemExpress;
import com.eghm.service.business.ItemExpressRegionService;
import com.eghm.service.business.ItemExpressService;
import com.eghm.utils.DataUtil;
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

    @Override
    public void create(ItemExpressAddRequest request) {
        ItemExpress express = DataUtil.copy(request, ItemExpress.class);
        itemExpressMapper.insert(express);
        itemExpressRegionService.createOrUpdate(express.getId(), request.getRegionList());
    }

    /**
     * @param request 模板信息
     */
    @Override
    public void update(ItemExpressEditRequest request) {
        ItemExpress selected = itemExpressMapper.selectById(request.getId());
        if (selected == null || !selected.getMerchantId().equals(request.getMerchantId())) {
            log.error("查询快递模板不合法 [{}] [{}]", request.getId(), request.getMerchantId());
            throw new BusinessException(ErrorCode.EXPRESS_NULL);
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

}
