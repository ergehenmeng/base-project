package com.eghm.model.dto.task;

import com.eghm.model.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @date 2019/9/6 17:47
 */
@Setter
@Getter
public class TaskQueryRequest extends PagingQuery {

    private static final long serialVersionUID = -7264160100372337195L;

    /**
     * 状态
     */
    @ApiModelProperty("状态 0:未开启 1:已开启")
    private Byte state;
}
