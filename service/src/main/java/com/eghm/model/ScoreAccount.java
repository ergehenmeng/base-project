package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商户积分账户表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("score_account")
public class ScoreAccount extends BaseEntity {

    @ApiModelProperty(value = "商户id")
    private Long merchantId;

    @ApiModelProperty(value = "可用积分")
    private Integer amount;

    @ApiModelProperty(value = "累计充值积分")
    private Integer rechargeAmount;

    @ApiModelProperty(value = "累计提现积分")
    private Integer withdrawAmount;

    @ApiModelProperty(value = "版本号")
    private Integer version;

}
