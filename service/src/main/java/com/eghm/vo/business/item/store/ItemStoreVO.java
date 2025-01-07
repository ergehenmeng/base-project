package com.eghm.vo.business.item.store;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/1/29
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemStoreVO {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "店铺名称")
    private String title;

    @Schema(description = "店铺logo")
    private String logoUrl;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "商家电话")
    private String telephone;

    @Schema(description = "商家介绍")
    private String introduce;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    private Integer state;
}
