package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.business.item.express.ExpressFeeCalcDTO;
import com.eghm.dto.business.item.express.ItemExpressAddRequest;
import com.eghm.dto.business.item.express.ItemExpressEditRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.ChargeMode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ItemExpressMapper;
import com.eghm.model.Item;
import com.eghm.model.ItemExpress;
import com.eghm.model.ItemExpressRegion;
import com.eghm.service.business.ItemExpressRegionService;
import com.eghm.service.business.ItemExpressService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public Integer calcFee(ExpressFeeCalcDTO dto) {
        List<Long> expressIds = dto.getItemList().stream().map(Item::getExpressId).filter(Objects::nonNull).collect(Collectors.toList());
        if (CollUtil.isEmpty(expressIds)) {
            log.info("所有商品都免邮费 [{}]", dto.getOrderList());
            return 0;
        }
        LambdaQueryWrapper<ItemExpress> wrapper = Wrappers.lambdaQuery();
        wrapper.select(ItemExpress::getId, ItemExpress::getChargeMode);
        wrapper.in(ItemExpress::getId, expressIds);
        List<ItemExpress> expressList = itemExpressMapper.selectList(wrapper);
        // 按计件或者计重进行分组
        Map<Integer, List<Long>> chargeMap = expressList.stream().collect(Collectors.groupingBy(ItemExpress::getChargeMode, Collectors.mapping(ItemExpress::getId, Collectors.toList())));

        // 快递总费用
        int totalFee = 0;
        // 计件商品快递费用计算
        List<Long> ids = chargeMap.get(ChargeMode.QUANTITY.getValue());
        if (CollUtil.isNotEmpty(ids)) {
            List<ItemExpressRegion> regionList = itemExpressRegionService.getList(ids);
            if (CollUtil.isNotEmpty(regionList)) {



            } else {
                log.info("商品配置的快递模板不存在 [{}]", ids);
            }
        }

        return null;
    }


}
