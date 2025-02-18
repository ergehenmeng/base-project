package com.eghm.dto.business.withdraw;

import com.eghm.convertor.YuanToCentDeserializer;
import com.eghm.enums.WithdrawState;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2025/2/17
 */
@Data
public class WithdrawNotifyDTO {

    @ApiModelProperty(value = "提现流水号")
    private String tradeNo;

    @ApiModelProperty(value = "第三方流水号")
    private String outRefundNo;

    @ApiModelProperty(value = "0:提现中 1:提现成功 2:提现失败")
    private WithdrawState state;

    @ApiModelProperty(value = "到账时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime withdrawTime;

    @ApiModelProperty(value = "到账金额")
    @JsonDeserialize(using = YuanToCentDeserializer.class)
    private Integer arrivalAmount;
}
