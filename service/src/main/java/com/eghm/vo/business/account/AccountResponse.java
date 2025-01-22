package com.eghm.vo.business.account;

import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/2/19
 */

@Data
public class AccountResponse {

    @Schema(description = "可提现金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer amount;

    @Schema(description = "支付冻结金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer payFreeze;

    @Schema(description = "提现冻结金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer withdrawFreeze;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
