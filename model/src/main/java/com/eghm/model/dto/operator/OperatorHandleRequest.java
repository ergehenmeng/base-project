package com.eghm.model.dto.operator;

import com.eghm.model.validation.annotation.OptionByte;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wyb
 * @date 2021/11/5 下午4:10
 */
@Data
@ApiModel("用户操作")
public class OperatorHandleRequest {

    @ApiModelProperty(value = "用户id", required = true)
    @NotNull(message = "用户id不能为空")
    private Long id;

    @ApiModelProperty(value = "操作类型 1:锁定用户 2:解锁用户 3:删除用户", required = true)
    @OptionByte(value = {1, 2, 3}, message = "操作类型非法")
    private Byte state;
}
