package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.WithdrawState;
import com.eghm.enums.WithdrawWay;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 商户提现记录
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("withdraw_log")
public class WithdrawLog extends BaseEntity {

    @ApiModelProperty(value = "商户id")
    private Long merchantId;

    @ApiModelProperty(value = "0:提现中 1:提现成功 2:提现失败")
    private WithdrawState state;

    @ApiModelProperty(value = "1:手动提现 2:自动提现")
    private WithdrawWay withdrawWay;

    @ApiModelProperty(value = "提现金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer amount;

    @ApiModelProperty(value = "提现手续费")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer fee;

    @ApiModelProperty(value = "提现流水号")
    private String refundNo;

    @ApiModelProperty(value = "第三方流水号")
    private String outRefundNo;

    @ApiModelProperty(value = "到账时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentTime;

    @ApiModelProperty(value = "银行卡所属用户姓名")
    private String realName;

    @ApiModelProperty(value = "银行卡类型")
    private String bankType;

    @ApiModelProperty(value = "银行卡号")
    private String bankNum;

    @ApiModelProperty(value = "备注信息")
    private String remark;

}
