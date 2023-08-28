package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.ItemRefundState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 商品订单表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("item_order")
@ApiModel(value="ItemOrder对象", description="商品订单表")
public class ItemOrder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    private Long itemId;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty("退款状态")
    private ItemRefundState refundState;

    @ApiModelProperty("数量")
    private Integer num;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "商品所属店铺")
    private Long storeId;

    @ApiModelProperty(value = "商品描述信息")
    private String depict;

    @ApiModelProperty(value = "封面图")
    private String coverUrl;

    @ApiModelProperty(value = "规格名称")
    private String skuTitle;

    @ApiModelProperty(value = "skuId")
    private Long skuId;

    @ApiModelProperty(value = "specId")
    private String specId;

    @ApiModelProperty(value = "封面图")
    private String skuCoverUrl;

    @ApiModelProperty(value = "销售价格")
    private Integer salePrice;

    @ApiModelProperty(value = "划线价")
    private Integer linePrice;

    @ApiModelProperty(value = "成本价")
    private Integer costPrice;

    @ApiModelProperty("快递费")
    private Integer expressFee;

    @ApiModelProperty(value = "购买须知")
    private String purchaseNotes;

    @ApiModelProperty(value = "限购数量")
    private Integer quota;

    @ApiModelProperty(value = "交付方式 0:无需发货 1:门店自提 2:快递包邮")
    private Boolean deliveryMethod;

    @ApiModelProperty(value = "商品介绍信息")
    private String introduce;

}
