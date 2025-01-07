package com.eghm.vo.business.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "可用积分")
    private Integer amount;

    @Schema(description = "支付冻结积分")
    private Integer payFreeze;

    @Schema(description = "提现冻结积分")
    private Integer withdrawFreeze;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
