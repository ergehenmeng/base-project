package com.eghm.service.business;

import com.eghm.vo.business.order.item.ExpressVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/10
 */
public interface ExpressService {

    /**
     * 查询快递信息 (顺丰)
     *
     * @param expressNo   快递单号
     * @param expressCode 快递公司编号
     * @param phone       收发货人手机号
     * @return 快递节点信息
     */
    List<ExpressVO> getExpressList(String expressNo, String expressCode, String phone);
}
