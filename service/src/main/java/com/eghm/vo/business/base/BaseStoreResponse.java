package com.eghm.vo.business.base;

import com.eghm.convertor.SplitterJsonSerializer;
import com.eghm.enums.ProductType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/19
 */

@Data
public class BaseStoreResponse {

    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "店铺名称")
    private String storeName;

    @Schema(description = "上下架状态 0:待上架 1:已上架")
    private Integer state;

    @Schema(description = "店铺名称")
    @JsonSerialize(using = SplitterJsonSerializer.class)
    private String coverUrl;

    @Schema(description = "店铺类型 ticket:门票 homestay:民宿 voucher:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;

    @Schema(description = "商户名称")
    private String merchantName;
}
