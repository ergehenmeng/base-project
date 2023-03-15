package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
@Getter
@AllArgsConstructor
public enum ProductType implements IEnum<String> {

    /**
     * 景区门票
     */
    TICKET("ticket", "门票", "MP", "defaultPayNotifyHandler"),

    /**
     * 餐饮券
     */
    VOUCHER("voucher", "餐饮券", "CY", "defaultPayNotifyHandler"),

    /**
     * 民宿
     */
    HOMESTAY("homestay", "民宿", "MS", "defaultPayNotifyHandler"),

    /**
     * 商品(文创/特产)
     */
    ITEM("item", "商品", "SP", "itemPayNotifyHandler"),

    /**
     * 线路
     */
    LINE("line", "线路", "XL", "defaultPayNotifyHandler")
    ;

    /**
     * 值
     */
    private final String value;

    /**
     * 名称
     */
    private final String name;

    /**
     * 前缀
     */
    private final String  prefix;

    /**
     * 异步通知处理bean
     */
    private final String payNotifyBean;

    @Override
    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ProductType of(String value) {
        return Arrays.stream(ProductType.values()).filter(productType -> productType.getValue().equals(value))
                .findFirst().orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_TYPE_MATCH));
    }

    public static ProductType prefix(String orderCode) {
        return Arrays.stream(ProductType.values()).filter(productType -> orderCode.startsWith(productType.getPrefix()))
                .findFirst().orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_TYPE_MATCH));
    }
}
