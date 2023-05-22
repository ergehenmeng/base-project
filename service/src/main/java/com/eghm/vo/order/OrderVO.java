package com.eghm.vo.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/9/28
 */

@Data
public class OrderVO<T> {

    @ApiModelProperty("结果状态 0: 处理中 1: 成功 2: 失败")
    private Integer state;

    @ApiModelProperty("错误信息")
    private String errorMsg;

    private T data;
}
