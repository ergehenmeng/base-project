package com.eghm.dto.business.redeem;

import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class RedeemCodeEditRequest {

    @ApiModelProperty(value = "id")
    @NotNull(message = "id不能为空")
    private Long id;

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

    @ApiModelProperty(value = "发放数量")
    @NotNull(message = "发放数量不能为空")
    @RangeInt(min = 1, max = 9999, message = "发放数量应在1-9999之间")
    private Integer num;
}
