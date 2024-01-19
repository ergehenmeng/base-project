package com.eghm.dto.task;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @since 2019/9/11 14:36
 */
@Getter
@Setter
public class TaskLogQueryRequest extends PagingQuery {

    @ApiModelProperty("状态: true:执行成功 false:执行失败")
    private Boolean state;

}
