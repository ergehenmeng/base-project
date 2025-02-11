package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.WithdrawState;
import com.eghm.enums.WithdrawWay;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "商户id")
    private Long merchantId;

    @Schema(description = "0:提现中 1:提现成功 2:提现失败")
    private WithdrawState state;

    @Schema(description = "1:手动提现 2:自动提现")
    private WithdrawWay withdrawWay;

    @Schema(description = "提现金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer amount;

    @Schema(description = "提现手续费")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer fee;

    @Schema(description = "提现流水号")
    private String refundNo;

    @Schema(description = "第三方流水号")
    private String outRefundNo;

    @Schema(description = "到账时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentTime;

    @Schema(description = "银行卡所属用户姓名")
    private String realName;

    @Schema(description = "银行卡类型")
    private String bankType;

    @Schema(description = "银行卡号")
    private String bankNum;

    @Schema(description = "备注信息")
    private String remark;

}
