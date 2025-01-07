package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单改价记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-03-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_adjust_log")
public class OrderAdjustLog extends BaseEntity {

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "操作人id")
    private Long userId;

    @Schema(description = "操作人姓名")
    private String userName;

    @Schema(description = "原价格")
    private Integer sourcePrice;

    @Schema(description = "新价格")
    private Integer targetPrice;

}
