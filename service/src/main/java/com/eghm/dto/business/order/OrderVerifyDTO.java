package com.eghm.dto.business.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author wyb
 * @since 2023/5/30
 */
@Data
public class OrderVerifyDTO {

    @ApiModelProperty(value = "订单号", required = true)
    @NotBlank(message = "订单号不能为空")
    private String orderNo;

    @ApiModelProperty(value = "待核销的游客id", required = true)
    @NotEmpty(message = "请选择要核销的游客")
    private List<Long> visitorList;

    @ApiModelProperty("备注信息")
    private String remark;
}
