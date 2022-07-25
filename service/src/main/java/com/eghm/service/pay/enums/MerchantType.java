package com.eghm.service.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 */
@AllArgsConstructor
@Getter
public enum MerchantType {

    /**
     * 二哥很猛
     */
    EGHM("EGHM", "二哥很猛");

    /**
     * 商户唯一code
     */
    private final String code;

    /**
     * 商户名称
     */
    private final String title;

}
