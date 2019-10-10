package com.fanyin.model.dto.business.task;

import com.fanyin.model.ext.PagingQuery;
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

    /**
     * 状态 true执行成功 false执行失败
     */
    private Boolean state;

}
