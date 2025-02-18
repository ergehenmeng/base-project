package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.RedeemGrantState;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 兑换码发放表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("redeem_code_grant")
public class RedeemCodeGrant extends BaseEntity {

    @ApiModelProperty(value = "配置id")
    private Long redeemCodeId;

    @ApiModelProperty(value = "cd名称")
    private String title;

    @ApiModelProperty(value = "cd_key")
    private String cdKey;

    @ApiModelProperty(value = "使用时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime useTime;

    @ApiModelProperty(value = "用户id")
    private Long memberId;

    @ApiModelProperty(value = "使用状态 0:待使用 1:已使用 2:已过期")
    private RedeemGrantState state;

    @ApiModelProperty("金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer amount;

    @ApiModelProperty(value = "有效开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "有效截止时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

}
