package com.eghm.vo.business.account;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/2/19
 */

@Data
public class AccountResponse {

    @ApiModelProperty(value = "可提现金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer amount;

    @ApiModelProperty(value = "支付冻结金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer payFreeze;

    @ApiModelProperty(value = "提现冻结金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer withdrawFreeze;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
