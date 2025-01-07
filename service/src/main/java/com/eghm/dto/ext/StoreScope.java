package com.eghm.dto.ext;

import com.eghm.enums.ref.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/19
 */
@Data
public class StoreScope {

    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "店铺类型 ticket:门票 homestay:民宿 voucher:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;
}
