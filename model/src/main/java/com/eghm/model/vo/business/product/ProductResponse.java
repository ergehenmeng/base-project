package com.eghm.model.vo.business.product;

import com.eghm.common.enums.ref.DeliveryType;
import com.eghm.common.enums.ref.RefundType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/12/30
 */
@Data
public class ProductResponse {

    @ApiModelProperty("商品id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "所属店铺")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long storeId;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty(value = "商品描述信息")
    private String depict;

    @ApiModelProperty(value = "封面图")
    private String coverUrl;

    @ApiModelProperty(value = "购买须知")
    private String purchaseNotes;

    @ApiModelProperty(value = "限购数量")
    private Integer quota;

    @ApiModelProperty(value = "交付方式 0:无须发货 1:门店自提 2:快递包邮")
    private DeliveryType deliveryType;

    @ApiModelProperty(value = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    private RefundType refundType;

    @ApiModelProperty("退款描述信息")
    private String refundDescribe;

    @ApiModelProperty(value = "虚拟销量")
    private Integer virtualNum;

    @ApiModelProperty(value = "商品介绍信息")
    private String introduce;

    @ApiModelProperty("sku列表")
    private List<ProductSkuResponse> skuList;
}
