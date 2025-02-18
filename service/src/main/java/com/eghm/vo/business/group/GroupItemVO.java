package com.eghm.vo.business.group;

import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/26
 */

@Data
public class GroupItemVO {

    @ApiModelProperty("商品ID")
    private Long itemId;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty("图片")
    private String coverUrl;

    @ApiModelProperty(value = "最低价格")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer minPrice;

    @ApiModelProperty(value = "销售数量(所有规格销售总量)")
    private Integer saleNum;

    @ApiModelProperty("拼团人数")
    private Integer groupNum;

    @ApiModelProperty("最大优惠金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer maxDiscountAmount;
}
