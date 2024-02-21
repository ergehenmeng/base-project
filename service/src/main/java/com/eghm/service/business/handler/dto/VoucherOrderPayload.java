package com.eghm.service.business.handler.dto;

import com.eghm.model.Restaurant;
import com.eghm.model.Voucher;
import lombok.Data;

/**
 * @author wyb
 * @since 2023/6/13
 */
@Data
public class VoucherOrderPayload {

    /**
     * 餐饮券信息
     */
    private Voucher voucher;

    /**
     * 餐厅信息
     */
    private Restaurant restaurant;

}
