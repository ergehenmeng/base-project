package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty(value = "操作人id")
    private Long userId;

    @ApiModelProperty(value = "操作人姓名")
    private String userName;

    @ApiModelProperty(value = "原价格")
    private Integer sourcePrice;

    @ApiModelProperty(value = "新价格")
    private Integer targetPrice;

}
