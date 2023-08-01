package com.eghm.vo.business.item.store;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/1/29
 */

@Data
public class ItemStoreVO {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("店铺名称")
    private String title;

    @ApiModelProperty(value = "店铺logo")
    private String logoUrl;

    @ApiModelProperty("封面图")
    private String coverUrl;

    @ApiModelProperty(value = "商家电话")
    private String telephone;

    @ApiModelProperty(value = "商家介绍")
    private String introduce;
}
