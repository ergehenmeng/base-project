package com.eghm.vo.business.order;

import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/9/28
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderCreateVO {

    @ApiModelProperty(value = "结果状态 0:处理中(#) 1:成功(@) 2:失败(&:系统异常, 其他则是业务异常)", required = true)
    private Integer state;

    @ApiModelProperty("错误信息")
    private String msg;

    @ApiModelProperty("异步查询key")
    private String key;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("支付金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer payAmount;
}
