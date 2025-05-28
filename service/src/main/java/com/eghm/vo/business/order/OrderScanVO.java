package com.eghm.vo.business.order;

import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/6/27
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderScanVO {

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("商品类型")
    private ProductType productType;

    @ApiModelProperty("总付款金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer payAmount;

    @ApiModelProperty("商品列表")
    private List<OrderProductVO> productList;
}
