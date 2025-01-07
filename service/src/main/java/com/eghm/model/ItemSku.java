package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 商品sku表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-06
 */
@Data
@TableName("item_sku")
@EqualsAndHashCode(callSuper = true)
public class ItemSku extends BaseEntity {

    @Schema(description = "零售id")
    private Long itemId;

    @Schema(description = "一级规格名")
    private String primarySpecValue;

    @Schema(description = "二级规格名")
    private String secondSpecValue;

    @Schema(description = "规格id,多个逗号分隔")
    private String specIds;

    @Schema(description = "成本价")
    private Integer costPrice;

    @Schema(description = "划线价")
    private Integer linePrice;

    @Schema(description = "销售价格")
    private Integer salePrice;

    @Schema(description = "剩余库存")
    private Integer stock;

    @Schema(description = "虚拟销量")
    private Integer virtualNum;

    @Schema(description = "销售量")
    private Integer saleNum;

    @Schema(description = "重量")
    private BigDecimal weight;

    @Schema(description = "sku图片(优先级最高)")
    private String skuPic;

}
