package com.eghm.vo.business.base;

import com.eghm.enums.ref.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/19
 */

@Data
public class BaseProductResponse {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "商品类型 ticket:门票 homestay:民宿 voucher:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;

    @Schema(description = "商品url")
    private String coverUrl;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "店铺名称")
    private String storeName;

    @Schema(description = "上下架状态 0:待上架 1:已上架")
    private Integer state;

    @Schema(description = "是否禁用 false:否 true:是")
    private Boolean disabled;
}
