package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static com.eghm.common.enums.ref.OrderState.*;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
@Getter
@AllArgsConstructor
public enum ProductType {

    /**
     * 景区门票
     */
    TICKET("ticket", "门票", "MP", new OrderState[] {
            NONE, UN_PAY, PROGRESS, UN_USED, VERIFY, COMPLETE, CLOSE
    }),

    /**
     * 餐饮券
     */
    VOUCHER("voucher", "餐饮券", "CY", new OrderState[] {
            NONE, UN_PAY, PROGRESS, UN_USED, VERIFY, COMPLETE, CLOSE
    }),

    /**
     * 民宿
     */
    HOMESTAY("homestay", "民宿", "MS", new OrderState[] {
            NONE, UN_PAY, PROGRESS, UN_USED, VERIFY, COMPLETE, CLOSE
    }),

    /**
     * 商品(文创/特产)
     */
    ITEM("item", "商品", "SP", new OrderState[] {
            NONE, UN_PAY, PROGRESS, WAIT_DELIVERY, WAIT_RECEIVE, APPRAISE, COMPLETE, CLOSE
    }),

    /**
     * 线路
     */
    LINE("line", "线路", "XL", new OrderState[] {
            NONE, UN_PAY, PROGRESS, UN_USED, VERIFY, COMPLETE, CLOSE
    })
    ;

    /**
     * 值
     */
    @EnumValue
    @JsonValue
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
     * 主订单状态定义 没有逻辑上的使用, 仅仅方便开发人员查看订单状态
     */
    private final OrderState[] stateDefine;

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
