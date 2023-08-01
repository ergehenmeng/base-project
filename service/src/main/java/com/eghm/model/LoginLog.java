package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户登陆日志
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
@TableName("login_log")
public class LoginLog extends BaseEntity {

    @ApiModelProperty("用户id")
    private Long memberId;

    @ApiModelProperty("登陆渠道")
    private String channel;

    @ApiModelProperty("登陆ip")
    private Long ip;

    @ApiModelProperty("设备厂商")
    private String deviceBrand;

    @ApiModelProperty("设备型号")
    private String deviceModel;

    @ApiModelProperty("软件版本")
    private String softwareVersion;

    @ApiModelProperty("设备唯一编号")
    private String serialNumber;

}