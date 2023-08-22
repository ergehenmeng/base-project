package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.business.item.express.ItemExpressRegionRequest;
import com.eghm.mapper.ItemExpressRegionMapper;
import com.eghm.model.ItemExpressRegion;
import com.eghm.service.business.ItemExpressRegionService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 快递模板区域 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-22
 */
@Slf4j
@AllArgsConstructor
@Service("itemExpressRegionService")
public class ItemExpressRegionServiceImpl implements ItemExpressRegionService {

    private final ItemExpressRegionMapper itemExpressRegionMapper;

    @Override
    public void createOrUpdate(Long templateId, List<ItemExpressRegionRequest> regionList) {
        LambdaUpdateWrapper<ItemExpressRegion> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ItemExpressRegion::getTemplateId, templateId);
        itemExpressRegionMapper.delete(wrapper);
        for (ItemExpressRegionRequest request : regionList) {
            ItemExpressRegion region = DataUtil.copy(request, ItemExpressRegion.class);
            region.setTemplateId(templateId);
            itemExpressRegionMapper.insert(region);
        }
    }
}
