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
public class PoiPointQueryRequest extends PagingQuery {

    @ApiModelProperty(value = "所属区域编号")
    private String areaCode;

    @ApiModelProperty(value = "点位类型")
    private Long typeId;
}
