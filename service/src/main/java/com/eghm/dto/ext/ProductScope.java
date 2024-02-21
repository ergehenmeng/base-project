package com.eghm.dto.ext;

import com.eghm.enums.ref.ProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/21
 */

@Data
public class ProductScope {

    @ApiModelProperty("商品id")
    private Long productId;

    @ApiModelProperty("商品类型")
    private ProductType productType;
}
