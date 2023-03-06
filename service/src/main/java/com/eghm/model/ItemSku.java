package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 商品sku表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("item_sku")
@ApiModel(value="ItemSku对象", description="商品sku表")
public class ItemSku extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "零售id")
    private Long itemId;

    @ApiModelProperty(value = "一级规格名")
    private String primarySpecValue;

    @ApiModelProperty(value = "二级规格名")
    private String secondSpecValue;

    @ApiModelProperty(value = "规格id,多个逗号分隔")
    private String specId;

    @ApiModelProperty(value = "成本价")
    private Integer costPrice;

    @ApiModelProperty(value = "划线价")
    private Integer linePrice;

    @ApiModelProperty(value = "销售价格")
    private Integer salePrice;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "虚拟销量")
    private Integer virtualNum;

    @ApiModelProperty(value = "销售量")
    private Integer saleNum;

    @ApiModelProperty(value = "sku图片(优先级最高)")
    private String skuPic;

}
