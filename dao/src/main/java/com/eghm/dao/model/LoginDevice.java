package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
@TableName("login_device")
public class LoginDevice extends BaseEntity {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("设备唯一序列号")
    private String serialNumber;

    @ApiModelProperty("设备型号")
    private String deviceModel;

    @ApiModelProperty("登陆ip")
    private Long ip;

    @ApiModelProperty("最近一次登陆的时间")
    private LocalDateTime loginTime;

}