package com.eghm.model.dto.homestay;

import com.eghm.model.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wyb 2022/6/25 15:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HomestayQueryRequest extends PagingQuery {

    @ApiModelProperty("上下架状态 0:待上架 1:已上架")
    private Integer state;
}
