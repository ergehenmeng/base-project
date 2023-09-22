package com.eghm.enums.ref;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Arrays;

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
    TICKET("ticket", "门票", "MP", new OrderState[]{
            OrderState.NONE, OrderState.UN_PAY, OrderState.PROGRESS, OrderState.UN_USED, OrderState.APPRAISE, OrderState.COMPLETE, OrderState.CLOSE
    }),

    /**
     * 餐饮券
     */
    RESTAURANT("restaurant", "餐饮券", "CY", new OrderState[]{
            OrderState.NONE, OrderState.UN_PAY, OrderState.PROGRESS, OrderState.UN_USED, OrderState.APPRAISE, OrderState.COMPLETE, OrderState.CLOSE
    }),

    /**
     * 民宿
     */
    HOMESTAY("homestay", "民宿", "MS", new OrderState[]{
            OrderState.NONE, OrderState.UN_PAY, OrderState.PROGRESS, OrderState.UN_USED, OrderState.APPRAISE, OrderState.COMPLETE, OrderState.CLOSE
    }),

    /**
     * 商品(文创/特产)
     */
    ITEM("item", "商品", "SP", new OrderState[]{
            OrderState.NONE, OrderState.UN_PAY, OrderState.PROGRESS, OrderState.WAIT_DELIVERY, OrderState.WAIT_RECEIVE, OrderState.APPRAISE, OrderState.COMPLETE, OrderState.CLOSE
    }),

    /**
     * 线路
     */
    LINE("line", "线路", "XL", new OrderState[]{
            OrderState.NONE, OrderState.UN_PAY, OrderState.PROGRESS, OrderState.UN_USED, OrderState.APPRAISE, OrderState.COMPLETE, OrderState.CLOSE
    });

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
    private final String prefix;

    /**
     * 主订单状态定义 没有逻辑上的使用, 仅仅方便开发人员查看订单状态
     */
    private final OrderState[] stateDefine;

    @JsonCreator
    public static ProductType of(String value) {
        return Arrays.stream(ProductType.values()).filter(productType -> productType.getValue().equals(value))
                .findFirst().orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_TYPE_MATCH));
    }

    /**
     * 根据订单号判断订单类型
     *
     * @param orderNo 订单号
     * @return 订单类型
     */
    public static ProductType prefix(String orderNo) {
        return Arrays.stream(ProductType.values()).filter(productType -> orderNo.startsWith(productType.getPrefix()))
                .findFirst().orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_TYPE_MATCH));
    }

    /**
     * 生成交易单号唯一Id
     *
     * @return 交易单号
     */
    public String generateTradeNo() {
        return prefix + DateUtil.formatShortLimit(LocalDate.now()) + IdWorker.getIdStr();
    }

    /**
     * 生成唯一订单号
     *
     * @return 订单号
     */
    public String generateOrderNo() {
        return prefix + IdWorker.getIdStr();
    }
}
