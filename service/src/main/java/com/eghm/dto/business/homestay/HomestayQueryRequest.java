package com.eghm.dto.business.homestay;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛 2022/6/25 15:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HomestayQueryRequest extends PagingQuery {

    @Schema(description = "上下架状态 0:待上架 1:已上架")
    private Integer state;

    @Schema(description = "商户id")
    private Long merchantId;
}
