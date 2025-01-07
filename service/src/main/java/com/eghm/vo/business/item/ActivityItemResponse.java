package com.eghm.vo.business.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/29
 */

@Data
public class ActivityItemResponse {

    @Schema(description = "商品id")
    private Long id;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "图片")
    @JsonIgnore
    private String coverUrl;

    @Schema(description = "规格列表")
    private List<BaseSkuResponse> skuList;
}
