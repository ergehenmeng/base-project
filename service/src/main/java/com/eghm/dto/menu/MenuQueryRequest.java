package com.eghm.dto.menu;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2024/5/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuQueryRequest extends PagingQuery {

    @ApiModelProperty("父菜单id")
    private String pid;
}
