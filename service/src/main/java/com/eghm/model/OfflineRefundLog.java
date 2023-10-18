package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 线下退款记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-06-14
 */
@Data
@TableName("offline_refund_log")
@EqualsAndHashCode(callSuper = true)
public class OfflineRefundLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "退款金额")
    private Integer refundAmount;

    @ApiModelProperty(value = "线下退款关联信息(json)")
    private String note;

    @ApiModelProperty(value = "退款凭证(转账记录)")
    private String certificate;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "用户id")
    private Long userId;

}
