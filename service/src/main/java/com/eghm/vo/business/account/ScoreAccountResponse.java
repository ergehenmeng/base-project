package com.eghm.vo.business.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 商户积分账户表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-12
 */
@Data
public class ScoreAccountResponse {

    @ApiModelProperty(value = "可用积分")
    private Integer amount;

    @ApiModelProperty(value = "支付冻结积分")
    private Integer payFreeze;

    @ApiModelProperty(value = "提现冻结积分")
    private Integer withdrawFreeze;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
