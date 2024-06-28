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

    @ApiModelProperty("商品类型 ticket:门票 homestay:民宿 restaurant:餐饮券 item:零售 line:线路 venue:场馆")
    private ProductType productType;

    @ApiModelProperty("商品url")
    private String coverUrl;

    @ApiModelProperty("商品名称")
    private String title;

    @ApiModelProperty("店铺名称")
    private String storeName;

    @ApiModelProperty("上下架状态 0:待上架 1:已上架")
    private Integer state;

}
