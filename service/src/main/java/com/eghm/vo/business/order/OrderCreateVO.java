package com.eghm.vo.business.order;

import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 订单创建结果
 * 0: 处理中 key不为空
 * 1: 成功  orderNo payAmount不为空
 * 2: 失败  msg不为空
 *
 * @author 二哥很猛
 * @since 2022/9/28
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderCreateVO {

    @Schema(description = "结果状态 0:处理中(#) 1:成功(@) 2:失败(&:系统异常, 其他则是业务异常)", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer state;

    @Schema(description = "错误信息")
    private String msg;

    @Schema(description = "异步查询key")
    private String key;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "支付金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer payAmount;
}
