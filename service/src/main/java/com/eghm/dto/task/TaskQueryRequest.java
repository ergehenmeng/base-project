package com.eghm.dto.task;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @since 2019/9/6 17:47
 */
@Setter
@Getter
public class TaskQueryRequest extends PagingQuery {

    @ApiModelProperty("状态 false:未开启 true:已开启")
    private Boolean state;
}
