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
 * 商品规格表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("product_sku")
@ApiModel(value="ProductSku对象", description="商品规格表")
public class ProductSku extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "规格名称")
    private String title;

    @ApiModelProperty(value = "划线价")
    private Integer linePrice;

    @ApiModelProperty(value = "成本价")
    private Integer costPrice;

    @ApiModelProperty(value = "销售价")
    private Integer salePrice;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "销售量")
    private Integer saleNum;

    @ApiModelProperty("封面图片")
    private String coverUrl;

}
