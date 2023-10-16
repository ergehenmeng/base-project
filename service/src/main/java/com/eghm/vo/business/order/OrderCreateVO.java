package com.eghm.vo.business.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/9/28
 */

@Data
public class OrderCreateVO<T> {

    @ApiModelProperty("结果状态 0: 处理中(#) 1: 成功(@) 2: 失败(&:系统异常, 其他则是业务异常)")
    private Integer state;

    @ApiModelProperty("错误信息")
    private String msg;

    private T data;
}
