package com.eghm.model.dto.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author 殿小二
 * @date 2020/9/14
 */
@Data
public class DeviceUnbindDTO {

    @NotEmpty(message = "设备序列号不能为空")
    @ApiModelProperty(required = true, value = "设备唯一序列号")
    private String serialNumber;
}
