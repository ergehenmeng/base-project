package com.eghm.dto.business.scenic;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @since 2022/6/14 23:00
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ScenicQueryRequest extends PagingQuery {

    @Schema(description = "上架状态 0:待上架 1:已上架")
    private Integer state;

    @Schema(description = "景区所属商户id")
    private Long merchantId;
}
