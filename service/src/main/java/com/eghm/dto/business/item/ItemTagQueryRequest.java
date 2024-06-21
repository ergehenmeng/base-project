package com.eghm.dto.business.item;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2023/8/9
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ItemTagQueryRequest extends PagingQuery {

    @ApiModelProperty("状态 true:正常 false:禁用")
    private Boolean state;
}
