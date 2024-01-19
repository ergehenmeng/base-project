package com.eghm.dto.business.item.sku;

import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.validation.annotation.RangeInt;
import com.eghm.validation.annotation.WordChecker;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 殿小二
 * @since 2023/3/6
 */
@Data
public class ItemSkuRequest {

    @ApiModelProperty("id(编辑时不能为空)")
    private Long id;

    @ApiModelProperty(value = "一级规格名(单规格为空)")
    @WordChecker(message = "一级规格存在敏感词")
    private String primarySpecValue;

    @ApiModelProperty(value = "二级规格名(单规格为空)")
    @WordChecker(message = "二级规格存在敏感词")
    private String secondSpecValue;

    @ApiModelProperty(value = "成本价")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer costPrice;

    @ApiModelProperty(value = "划线价")
    @NotNull(message = "划线价不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer linePrice;

    @ApiModelProperty(value = "销售价格")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    @NotNull(message = "销售价不能为空不能为空")
    private Integer salePrice;

    @ApiModelProperty("重量")
    private BigDecimal weight;

    @ApiModelProperty(value = "库存")
    @RangeInt(max = 9999, message = "库存数应为0~9999")
    private Integer stock;

    @ApiModelProperty(value = "虚拟销量")
    @RangeInt(max = 9999, message = "虚拟销量应为0~9999")
    private Integer virtualNum;

    @ApiModelProperty(value = "sku图片(优先级最高)")
    private String skuPic;
}
