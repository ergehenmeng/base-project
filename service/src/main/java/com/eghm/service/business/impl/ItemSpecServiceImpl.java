package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.business.item.sku.ItemSpecRequest;
import com.eghm.mapper.ItemSpecMapper;
import com.eghm.model.Item;
import com.eghm.model.ItemSpec;
import com.eghm.service.business.ItemSpecService;
import com.eghm.utils.DataUtil;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 殿小二
 * @since 2023/3/6
 */
@Service("itemSpecService")
@Slf4j
@AllArgsConstructor
public class ItemSpecServiceImpl implements ItemSpecService {

    private final ItemSpecMapper itemSpecMapper;

    @Override
    public Map<String, Long> insert(Item item, List<ItemSpecRequest> specList) {
        Map<String, Long> specMap = new HashMap<>(8);
        if (Boolean.TRUE.equals(item.getMultiSpec())) {
            for (ItemSpecRequest request : specList) {
                ItemSpec spec = DataUtil.copy(request, ItemSpec.class, "id");
                spec.setItemId(item.getId());
                itemSpecMapper.insert(spec);
                specMap.put(spec.getSpecValue(), spec.getId());
            }
        }
        return specMap;
    }

    @Override
    public Map<String, Long> update(Item item, List<ItemSpecRequest> specList) {
        if (Boolean.FALSE.equals(item.getMultiSpec())) {
            this.deleteByItemId(item.getId());
            return Maps.newHashMap();
        }
        List<Long> specIds = specList.stream().map(ItemSpecRequest::getId).filter(Objects::nonNull).collect(Collectors.toList());
        this.deleteByNotInIds(item.getId(), specIds);

        Map<String, Long> specMap = new HashMap<>(8);
        for (ItemSpecRequest request : specList) {
            ItemSpec spec = DataUtil.copy(request, ItemSpec.class);
            spec.setItemId(item.getId());
            if (spec.getId() == null) {
                itemSpecMapper.insert(spec);
            } else {
                itemSpecMapper.updateById(spec);
            }
            specMap.put(spec.getSpecValue(), spec.getId());
        }
        return specMap;
    }

    @Override
    public List<ItemSpec> getByItemId(Long itemId) {
        LambdaQueryWrapper<ItemSpec> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ItemSpec::getItemId, itemId);
        return itemSpecMapper.selectList(wrapper);
    }

    @Override
    public Map<Long, ItemSpec> getByIdMap(Set<Long> itemIds) {
        LambdaQueryWrapper<ItemSpec> wrapper = Wrappers.lambdaQuery();
        wrapper.in(ItemSpec::getItemId, itemIds);
        List<ItemSpec> specList = itemSpecMapper.selectList(wrapper);
        return specList.stream().collect(Collectors.toMap(ItemSpec::getId, Function.identity()));
    }

    /**
     * 删除不在指定id列表中的规格信息
     *
     * @param itemId  商品id
     * @param specIds 规格id
     */
    private void deleteByNotInIds(Long itemId, List<Long> specIds) {
        LambdaUpdateWrapper<ItemSpec> wrapper = Wrappers.lambdaUpdate();
        wrapper.notIn(CollUtil.isNotEmpty(specIds), ItemSpec::getId, specIds);
        wrapper.eq(ItemSpec::getItemId, itemId);
        itemSpecMapper.delete(wrapper);
    }

    /**
     * 删除商品下所有规格信息
     *
     * @param id id
     */
    private void deleteByItemId(Long id) {
        LambdaUpdateWrapper<ItemSpec> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ItemSpec::getItemId, id);
        itemSpecMapper.delete(wrapper);
    }
}
