package com.eghm.vo.business.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/9/28
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderCreateVO<T> {

    @Schema(description = "结果状态 0:处理中(#) 1:成功(@) 2:失败(&:系统异常, 其他则是业务异常)")
    private Integer state;

    @Schema(description = "错误信息")
    private String msg;

    private T data;
}
