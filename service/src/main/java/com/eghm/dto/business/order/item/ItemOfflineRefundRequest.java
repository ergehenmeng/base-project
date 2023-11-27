package com.eghm.dto.business.order.item;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDecoder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wyb
 * @since 2023/6/14
 */
@Data
public class ItemOfflineRefundRequest {

    @ApiModelProperty(value = "订单编号", required = true)
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

    @ApiModelProperty(value = "商品订单ID", required = true)
    @NotEmpty(message = "请选择要退款的商品")
    private List<Long> itemList;

    @ApiModelProperty(value = "退款金额", required = true)
    @Min(value = 1, message = "退款金额最少0.01元")
    @NotNull(message = "退款金额不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer refundAmount;

    @ApiModelProperty(value = "退款凭证(转账记录)", required = true)
    @NotBlank(message = "退款凭证不能为空")
    private String certificate;

    @ApiModelProperty(value = "备注信息", required = true)
    @NotBlank(message = "备注信息不能为空")
    private String remark;

    @Assign
    @ApiModelProperty(value = "用户id", hidden = true)
    private Long userId;

}
