package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商户积分变动明细表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("score_account_log")
public class ScoreAccountLog extends BaseEntity {

    @ApiModelProperty(value = "商户id")
    private Long merchantId;

    @ApiModelProperty(value = "积分变动类型")
    private Integer chargeType;

    @ApiModelProperty(value = "变动积分")
    private Integer amount;

    @ApiModelProperty(value = "1:收入 2:支出")
    private Integer direction;

    @ApiModelProperty(value = "变动后的积分")
    private Integer surplusAmount;

    @ApiModelProperty(value = "备注信息")
    private String remark;

}
