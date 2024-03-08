package com.eghm.dto.ext;

import com.eghm.enums.ref.ProductType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/10/25
 */
@Data
public class CalcStatistics {

    @ApiModelProperty("店铺Id")
    private Long storeId;

    @ApiModelProperty("商品id")
    private Long productId;

    @ApiModelProperty("商品类型 ticket:门票 homestay:民宿 restaurant:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;
}
