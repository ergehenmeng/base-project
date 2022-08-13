package com.eghm.model.dto.task;

import com.eghm.model.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @date 2019/9/11 14:36
 */
@Getter
@Setter
public class TaskLogQueryRequest extends PagingQuery {

    private static final long serialVersionUID = -420375186002438142L;

    @ApiModelProperty("状态: true:执行成功 false:执行失败")
    private Boolean state;

}
