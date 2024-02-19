package com.eghm.dto.ext;

import com.eghm.enums.ref.ProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/19
 */
@Data
public class StoreScope {

    @ApiModelProperty("店铺id")
    private Long storeId;

    @ApiModelProperty("店铺类型")
    private ProductType productType;
}
