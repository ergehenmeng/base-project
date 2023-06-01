package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eghm.mapper.OrderVisitorRefundMapper;
import com.eghm.model.OrderVisitorRefund;
import com.eghm.service.business.OrderVisitorRefundService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 游客退款记录关联表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-06-01
 */
@Service
@AllArgsConstructor
public class OrderVisitorRefundServiceImpl implements OrderVisitorRefundService {

    private final OrderVisitorRefundMapper orderVisitorRefundMapper;
}
