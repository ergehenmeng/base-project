package com.eghm.vo.business.item.store;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/1/29
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @ApiModelProperty("状态 0:待上架 1:已上架 2:强制下架")
    private Integer state;
}
