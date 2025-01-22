package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "商户id")
    private Long merchantId;

    @Schema(description = "可提现金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer amount;

    @Schema(description = "支付冻结金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer payFreeze;

    @Schema(description = "提现冻结金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer withdrawFreeze;

    @Schema(description = "版本号")
    private Integer version;

}
