package com.eghm.dto.business.redeem;

import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/2/10
 */

@Data
public class RedeemCodeAddRequest {

    @ApiModelProperty(value = "兑换码名称")
    @NotBlank(message = "兑换码名称不能为空")
    @Size(max = 20, message = "兑换码名称最大20字符")
    private String title;

    @ApiModelProperty(value = "有效开始时间")
    @NotNull(message = "开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "有效截止时间")
    @NotNull(message = "结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty("金额")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer amount;

    @ApiModelProperty(value = "发放数量")
    @NotNull(message = "发放数量不能为空")
    @RangeInt(min = 1, max = 999, message = "发放数量应在1-999之间")
    private Integer num;
}
