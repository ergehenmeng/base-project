package com.eghm.vo.business.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/29
 */

@Data
public class ActivityItemResponse {

    @ApiModelProperty(value = "商品id")
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty("图片")
    @JsonIgnore
    private String coverUrl;

    @ApiModelProperty("规格列表")
    private List<BaseSkuResponse> skuList;
}
