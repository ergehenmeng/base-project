package com.eghm.dto.sys.task;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @since 2019/9/11 14:36
 */
@Getter
@Setter
public class TaskLogQueryRequest extends PagingQuery {

    @Schema(description = "状态: true:执行成功 false:执行失败")
    private Boolean state;

}
