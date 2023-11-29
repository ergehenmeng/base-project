package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.business.order.item.ItemSippingRequest;
import com.eghm.mapper.ItemOrderExpressMapper;
import com.eghm.model.ItemOrderExpress;
import com.eghm.service.business.ItemOrderExpressService;
import com.eghm.service.common.JsonService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.order.item.ExpressVO;
import com.eghm.vo.business.order.item.FirstExpressVO;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @since 2023/11/27
 */

@Slf4j
@Service("itemOrderExpressService")
@AllArgsConstructor
public class ItemOrderExpressServiceImpl implements ItemOrderExpressService {

    private final ItemOrderExpressMapper itemOrderExpressMapper;

    private final JsonService jsonService;

    @Override
    public void insert(ItemSippingRequest request) {
        List<Long> orderIds = request.getOrderIds();
        for (Long orderId : orderIds) {
            ItemOrderExpress express = DataUtil.copy(request, ItemOrderExpress.class);
            express.setItemOrderId(orderId);
            itemOrderExpressMapper.insert(express);
        }
    }

    @Override
    public List<FirstExpressVO> getFirstExpressList(String orderNo) {
        LambdaQueryWrapper<ItemOrderExpress> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ItemOrderExpress::getOrderNo, orderNo);
        List<ItemOrderExpress> selectList = itemOrderExpressMapper.selectList(wrapper);
        if (selectList.isEmpty()) {
            return Lists.newArrayList();
        }
        Map<String, ItemOrderExpress> expressMap = selectList.stream().collect(Collectors.toMap(ItemOrderExpress::getExpressNo, Function.identity(), (o1, o2) -> o1));
        // 如果只有一条物流信息,则直接返回(减少不必要的代码判断)
        return this.getFirstExpress(expressMap.values());
    }

    @Override
    public ItemOrderExpress selectById(Long id) {
        return itemOrderExpressMapper.selectById(id);
    }

    /**
     * 计算每个物流的最新节点
     *
     * @param expressList 物流信息
     * @return list
     */
    public List<FirstExpressVO> getFirstExpress(Collection<ItemOrderExpress> expressList) {
        List<FirstExpressVO> voList = Lists.newArrayList();
        for (ItemOrderExpress express : expressList) {
            List<ExpressVO> vos = jsonService.fromJsonList(express.getContent(), ExpressVO.class);
            if (CollUtil.isNotEmpty(vos)) {
                FirstExpressVO vo = new FirstExpressVO();
                vo.setId(express.getId());
                vo.setContext(vos.get(0).getContext());
                voList.add(vo);
            }
        }
        return voList;
    }
}
