package com.eghm.dto.sys.task;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @since 2019/9/6 17:47
 */
@Setter
@Getter
public class TaskQueryRequest extends PagingQuery {

    @Schema(description = "状态 false:未开启 true:已开启")
    private Boolean state;
}
