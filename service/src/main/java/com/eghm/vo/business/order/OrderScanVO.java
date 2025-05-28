package com.eghm.vo.business.order;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/6/27
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderScanVO {

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "商品类型")
    private ProductType productType;

    @Schema(description = "总付款金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer payAmount;

    @Schema(description = "订单中的产品列表")
    private List<OrderProductVO> productList;
}
