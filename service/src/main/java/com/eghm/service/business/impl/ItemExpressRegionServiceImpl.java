package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.business.item.express.ExpressFeeCalcDTO;
import com.eghm.dto.business.item.express.ItemCalcDTO;
import com.eghm.dto.business.item.express.ItemExpressRegionRequest;
import com.eghm.dto.business.item.express.ItemMergeCalcDTO;
import com.eghm.enums.ref.ChargeMode;
import com.eghm.mapper.ItemExpressRegionMapper;
import com.eghm.model.ItemExpressRegion;
import com.eghm.service.business.ItemExpressRegionService;
import com.eghm.service.common.JsonService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private final JsonService jsonService;

    @Override
    public void createOrUpdate(Long expressId, List<ItemExpressRegionRequest> regionList) {
        LambdaUpdateWrapper<ItemExpressRegion> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ItemExpressRegion::getExpressId, expressId);
        itemExpressRegionMapper.delete(wrapper);
        for (ItemExpressRegionRequest request : regionList) {
            ItemExpressRegion region = DataUtil.copy(request, ItemExpressRegion.class);
            region.setExpressId(expressId);
            itemExpressRegionMapper.insert(region);
        }
    }

    @Override
    public List<ItemExpressRegion> getList(List<Long> expressIds) {
        LambdaQueryWrapper<ItemExpressRegion> wrapper = Wrappers.lambdaQuery();
        wrapper.in(ItemExpressRegion::getExpressId, expressIds);
        return itemExpressRegionMapper.selectList(wrapper);
    }


    @Override
    public Integer calcFee(ExpressFeeCalcDTO dto) {
        // 免运费的不参与计算
        List<ItemCalcDTO> filterList = dto.getOrderList().stream().filter(itemCalcDTO -> itemCalcDTO.getExpressId() != null).collect(Collectors.toList());

        List<Long> expressIds = filterList.stream().map(ItemCalcDTO::getExpressId).collect(Collectors.toList());
        if (CollUtil.isEmpty(expressIds)) {
            log.info("所有商品都免邮费 [{}]", dto.getOrderList());
            return 0;
        }
        // 查询所有的物流信息,并按模板进行划分
        List<ItemExpressRegion> allRegionList = this.getList(expressIds);
        Map<Long, List<ItemExpressRegion>> expressRegionMap = allRegionList.stream().collect(Collectors.groupingBy(ItemExpressRegion::getExpressId, Collectors.toList()));

        // 按计件还是计费进行分组, 因为两套计算逻辑不一样
        Map<Integer, List<ItemCalcDTO>> chargeMap = filterList.stream().collect(Collectors.groupingBy(ItemCalcDTO::getChargeMode, Collectors.toList()));

        List<ItemCalcDTO> dtoList = chargeMap.get(ChargeMode.QUANTITY.getValue());
        if (CollUtil.isNotEmpty(dtoList)) {
            log.info("存在计件商品, 开始进行费用计算 [{}]", jsonService.toJson(dtoList));
            List<ItemMergeCalcDTO> mergeList = this.mergeItem(filterList);
            for (ItemMergeCalcDTO calcDTO : mergeList) {
                List<ItemExpressRegion> regionList = expressRegionMap.get(calcDTO.getExpressId());
                // TODO
            }
        }
        return 0;

    }

    /**
     * 同一商品多sku情况下,物流模板是同一种,因此需要合并数量,方便计件时计算费用
     * @param orderList 下单商品
     * @return 合并数量后的商品
     */
    private List<ItemMergeCalcDTO> mergeItem(List<ItemCalcDTO> orderList) {

        if (orderList.size() == 1) {
            return DataUtil.copy(orderList, ItemMergeCalcDTO.class);
        }

        Map<Long, List<ItemCalcDTO>> collected = orderList.stream().collect(Collectors.groupingBy(ItemCalcDTO::getItemId, Collectors.toList()));
        List<ItemMergeCalcDTO> dtoList = new ArrayList<>();
        for (Map.Entry<Long, List<ItemCalcDTO>> entry : collected.entrySet()) {
            ItemMergeCalcDTO dto = new ItemMergeCalcDTO();
            dto.setItemId(entry.getKey());
            // 同一个商品模板都是一样, 因此直接取第一个即可
            ItemCalcDTO calcDTO = entry.getValue().get(0);
            dto.setChargeMode(calcDTO.getChargeMode());
            dto.setExpressId(calcDTO.getExpressId());
            dto.setNum(entry.getValue().stream().mapToInt(ItemCalcDTO::getNum).sum());
            dtoList.add(dto);
        }
        return dtoList;
    }
}
