package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "商户id")
    private Long merchantId;

    @Schema(description = "可用积分")
    private Integer amount;

    @Schema(description = "支付冻结积分")
    private Integer payFreeze;

    @Schema(description = "提现冻结积分")
    private Integer withdrawFreeze;

    @Schema(description = "版本号")
    private Integer version;

}
