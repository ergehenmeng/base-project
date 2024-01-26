package com.eghm.service.business.impl;

import com.eghm.model.LimitPurchaseItem;
import com.eghm.mapper.LimitPurchaseItemMapper;
import com.eghm.service.business.LimitPurchaseItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 限时购商品表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-26
 */

@Slf4j
@AllArgsConstructor
@Service("limitPurchaseItemService")
public class LimitPurchaseItemServiceImpl implements LimitPurchaseItemService {

    private LimitPurchaseItemMapper limitPurchaseItemMapper;
}
