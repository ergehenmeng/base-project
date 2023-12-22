package com.eghm.dto.poi;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/12/21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PoiLineQueryRequest extends PagingQuery {

    @ApiModelProperty(value = "所属区域编号")
    private String areaCode;

}
