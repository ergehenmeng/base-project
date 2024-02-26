package com.eghm.vo.business.limit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/29
 */

@Data
public class LimitItemResponse {

    @ApiModelProperty("商品id")
    private Long itemId;

    @ApiModelProperty("商品名称")
    private String title;

    @ApiModelProperty("商品图片")
    private String coverUrl;

    @ApiModelProperty("规格优惠配置json")
    @JsonIgnore
    private String skuValue;

    @ApiModelProperty("规格优惠列表")
    private List<LimitSkuResponse> skuList;
}
