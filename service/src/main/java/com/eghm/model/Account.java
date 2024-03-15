package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商户账户信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */
@Data
@TableName("account")
@EqualsAndHashCode(callSuper = false)
public class Account extends BaseEntity {

    @ApiModelProperty(value = "商户id")
    private Long merchantId;

    @ApiModelProperty(value = "可提现金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer amount;

    @ApiModelProperty(value = "支付冻结金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer payFreeze;

    @ApiModelProperty(value = "提现冻结金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer withdrawFreeze;

    @ApiModelProperty(value = "版本号")
    private Integer version;

}
