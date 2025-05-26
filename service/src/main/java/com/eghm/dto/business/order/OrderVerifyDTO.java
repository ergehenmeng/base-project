package com.eghm.dto.business.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
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

    @ApiModelProperty(value = "游客id或订单商品id")
    private List<Long> ids;

    @ApiModelProperty(value = "套票票子订单id(只有门票订单且为套票票才需要该字段)")
    private Long combineId;

    @ApiModelProperty("备注信息")
    private String remark;
}
