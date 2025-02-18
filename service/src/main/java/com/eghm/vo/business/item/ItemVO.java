package com.eghm.vo.business.item;

import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/12/30
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ItemVO {

    @ApiModelProperty(value = "商品id")
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty("图片")
    private String coverUrl;

    @ApiModelProperty(value = "最低价格")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer minPrice;

    @ApiModelProperty(value = "销售数量(所有规格销售总量)")
    private Integer saleNum;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("店铺id")
    private Long storeId;

    @ApiModelProperty("商品状态 0:待上架 1:已上架 2:强制下架")
    private Integer state;
}
