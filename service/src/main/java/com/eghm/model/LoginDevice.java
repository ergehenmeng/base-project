package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
@TableName("login_device")
public class LoginDevice {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("用户id")
    private Long memberId;

    @ApiModelProperty("设备唯一序列号")
    private String serialNumber;

    @ApiModelProperty("设备型号")
    private String deviceModel;

    @ApiModelProperty("登陆ip")
    private Long ip;

    @ApiModelProperty("最近一次登陆的时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;

}