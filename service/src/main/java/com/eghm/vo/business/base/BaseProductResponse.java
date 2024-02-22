package com.eghm.vo.business.base;

import com.eghm.enums.ref.ProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/19
 */

@Data
public class BaseProductResponse {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("商户类型")
    private ProductType productType;

    @ApiModelProperty("店铺名称")
    private String coverUrl;

    @ApiModelProperty("商品名称")
    private String title;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("上下架状态 0:待上架 1:已上架")
    private Integer state;

}
