package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.JsonService;
import com.eghm.dto.business.item.express.ExpressFeeCalcDTO;
import com.eghm.dto.business.item.express.ExpressTemplateRegionRequest;
import com.eghm.dto.business.item.express.ItemCalcDTO;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.ChargeMode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ExpressTemplateRegionMapper;
import com.eghm.model.ExpressTemplateRegion;
import com.eghm.model.ItemSku;
import com.eghm.service.business.ExpressTemplateRegionService;
import com.eghm.service.business.ItemSkuService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
@Service("expressTemplateRegionService")
public class ExpressTemplateRegionServiceImpl implements ExpressTemplateRegionService {

    private final JsonService jsonService;

    private final ItemSkuService itemSkuService;

    private final ExpressTemplateRegionMapper expressTemplateRegionMapper;

    @Override
    public void createOrUpdate(Long expressId, List<ExpressTemplateRegionRequest> regionList) {
        LambdaUpdateWrapper<ExpressTemplateRegion> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ExpressTemplateRegion::getExpressId, expressId);
        expressTemplateRegionMapper.delete(wrapper);
        for (ExpressTemplateRegionRequest request : regionList) {
            ExpressTemplateRegion region = DataUtil.copy(request, ExpressTemplateRegion.class);
            region.setExpressId(expressId);
            expressTemplateRegionMapper.insert(region);
        }
    }

    @Override
    public Integer calcFee(ExpressFeeCalcDTO dto) {
        // 免运费的不参与计算
        List<ItemCalcDTO> filterList = dto.getOrderList().stream().filter(itemCalcDTO -> itemCalcDTO.getExpressId() != null).collect(Collectors.toList());
        if (CollUtil.isEmpty(filterList)) {
            log.info("所有商品都免邮费 [{}]", dto.getOrderList());
            return 0;
        }
        List<Long> expressIds = filterList.stream().map(ItemCalcDTO::getExpressId).collect(Collectors.toList());
        // 查询所有的物流信息,并按模板进行划分
        List<ExpressTemplateRegion> allRegionList = this.getList(expressIds);
        Map<Long, List<ExpressTemplateRegion>> expressRegionMap = allRegionList.stream().collect(Collectors.groupingBy(ExpressTemplateRegion::getExpressId, Collectors.toList()));
        // 按计件还是计费进行分组, 因为两套计算逻辑不一样
        Map<Integer, List<ItemCalcDTO>> chargeMap = filterList.stream().collect(Collectors.groupingBy(ItemCalcDTO::getChargeMode, Collectors.toList()));
        int totalFee = 0;
        // 按重量计费
        List<ItemCalcDTO> weightList = chargeMap.get(ChargeMode.WEIGHT.getValue());
        if (CollUtil.isNotEmpty(weightList)) {
            log.info("存在计重商品, 开始进行运费计算 [{}]", jsonService.toJson(weightList));
            Map<Long, ItemSku> skuMap = itemSkuService.getByIdShelveMap(weightList.stream().map(ItemCalcDTO::getSkuId).collect(Collectors.toSet()));
            this.checkSkuItemMapping(skuMap, weightList);
            for (ItemCalcDTO calcDTO : weightList) {
                List<ExpressTemplateRegion> regionList = expressRegionMap.get(calcDTO.getExpressId());
                int expressFee = this.calcExpressWeight(calcDTO.getItemId(), calcDTO.getWeight(), dto.getCountyId(), regionList);
                calcDTO.setExpressFee(expressFee);
                totalFee += expressFee;
                log.info("当前商品计重快递费 itemId: [{}] num:[{}] countyId: [{}] 快递费:[{}]", calcDTO.getItemId(), calcDTO.getNum(), dto.getCountyId(), expressFee);
            }
        }
        // 按数量计费
        List<ItemCalcDTO> quantityList = chargeMap.get(ChargeMode.QUANTITY.getValue());
        if (CollUtil.isNotEmpty(quantityList)) {
            log.info("存在计件商品, 开始进行运费计算 [{}]", jsonService.toJson(quantityList));
            for (ItemCalcDTO calcDTO : quantityList) {
                List<ExpressTemplateRegion> regionList = expressRegionMap.get(calcDTO.getExpressId());
                int expressFee = this.calcExpressQuantity(calcDTO.getItemId(), calcDTO.getNum(), dto.getCountyId(), regionList);
                calcDTO.setExpressFee(expressFee);
                totalFee += expressFee;
                log.info("当前商品计件快递费 itemId: [{}] num:[{}] countyId: [{}] 快递费:[{}]", calcDTO.getItemId(), calcDTO.getNum(), dto.getCountyId(), expressFee);
            }
        }
        return totalFee;
    }

    /**
     * 批量查询快递区域价格配置信息
     *
     * @param expressIds 快递模板id
     * @return 配置信息
     */
    private List<ExpressTemplateRegion> getList(List<Long> expressIds) {
        LambdaQueryWrapper<ExpressTemplateRegion> wrapper = Wrappers.lambdaQuery();
        wrapper.in(ExpressTemplateRegion::getExpressId, expressIds);
        return expressTemplateRegionMapper.selectList(wrapper);
    }

    /**
     * 校验skuId与itemId是否匹配,同时设置skuId的重量
     *
     * @param skuMap     sku信息
     * @param weightList 下单商品信息
     */
    private void checkSkuItemMapping(Map<Long, ItemSku> skuMap, List<ItemCalcDTO> weightList) {
        for (ItemCalcDTO dto : weightList) {
            ItemSku sku = skuMap.get(dto.getSkuId());
            if (!dto.getItemId().equals(sku.getItemId())) {
                throw new BusinessException(ErrorCode.SKU_ITEM_MATCH);
            }
            dto.setWeight(sku.getWeight());
        }
    }

    /**
     * 计算快递费(计重)
     *
     * @param itemId     商品id
     * @param weight     重量
     * @param countyId   收货县区
     * @param regionList 价格配置
     * @return 运费
     */
    private int calcExpressWeight(Long itemId, BigDecimal weight, Long countyId, List<ExpressTemplateRegion> regionList) {
        ExpressTemplateRegion region = this.matchRegion(countyId, regionList, itemId);
        BigDecimal firstPart = region.getFirstPart();
        // 数量不超过首件
        if (weight.compareTo(firstPart) <= 0) {
            return region.getFirstPrice();
        }
        // 超过首件的部分
        BigDecimal next = weight.subtract(firstPart);
        BigDecimal nextPart = region.getNextPart();
        BigDecimal value = next.divide(nextPart, 2, RoundingMode.HALF_UP);
        int nextNum;
        // 判断是否除尽
        if (value.scale() == 0) {
            nextNum = value.intValue();
        } else {
            nextNum = value.intValue() + 1;
        }
        // 向上取证
        return region.getFirstPrice() + nextNum * region.getNextUnitPrice();
    }

    /**
     * 计算快递费(计件)
     *
     * @param itemId     商品id
     * @param num        商品构面数量(同一商品多sku数量会相加)
     * @param countyId   收货县区
     * @param regionList 价格配置
     * @return 运费
     */
    private int calcExpressQuantity(Long itemId, Integer num, Long countyId, List<ExpressTemplateRegion> regionList) {
        ExpressTemplateRegion region = this.matchRegion(countyId, regionList, itemId);
        int firstPart = region.getFirstPart().intValue();
        // 数量不超过首件
        if (num <= firstPart) {
            return region.getFirstPrice();
        }
        // 超过首件的部分
        int next = num - firstPart;
        int nextPart = region.getNextPart().intValue();
        // 向上取证
        int nextNum = (next % nextPart) == 0 ? (next / nextPart) : (next / nextPart + 1);
        return region.getFirstPrice() + nextNum * region.getNextUnitPrice();
    }

    private ExpressTemplateRegion matchRegion(Long countyId, List<ExpressTemplateRegion> regionList, Long itemId) {
        return regionList.stream().filter(expressTemplateRegion -> expressTemplateRegion.getRegionCode().contains(String.valueOf(countyId))).findFirst()
                .orElseThrow(() -> {
                    log.error("该地区未配置快递模板, 不支持配送 [{}] [{}]", itemId, countyId);
                    return new BusinessException(ErrorCode.EXPRESS_NOT_SUPPORT);
                });
    }
}
