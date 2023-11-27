package com.eghm.service.business.impl;

import com.eghm.mapper.ItemOrderExpressMapper;
import com.eghm.service.business.ItemOrderExpressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2023/11/27
 */

@Slf4j
@Service("itemOrderExpressService")
@AllArgsConstructor
public class ItemOrderExpressServiceImpl implements ItemOrderExpressService {

    private final ItemOrderExpressMapper itemOrderExpressMapper;
}
