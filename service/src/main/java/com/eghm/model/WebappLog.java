package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统异常记录
 * @author 二哥很猛
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("webapp_log")
public class WebappLog implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("用户id")
    private Long memberId;

    @ApiModelProperty("软件版本号 针对app端,例如 1.2.7")
    private String version;

    @ApiModelProperty("注册渠道 PC,ANDROID,IOS,H5,WECHAT,ALIPAY")
    private String channel;

    @ApiModelProperty("客户端平台版本号 ios: 10.4.1,android:8.1.0")
    private String osVersion;

    @ApiModelProperty("设备厂商")
    private String deviceBrand;

    @ApiModelProperty("设备型号")
    private String deviceModel;

    @ApiModelProperty("设备唯一编号")
    private String serialNumber;

    @ApiModelProperty("访问ip")
    private String ip;

    @ApiModelProperty("访问耗时")
    private Long elapsedTime;

    @ApiModelProperty("访问链接")
    private String url;

    @ApiModelProperty("请求参数(json)")
    private String requestParam;

    @ApiModelProperty("请求堆栈id")
    private String traceId;

    @ApiModelProperty("错误日志")
    private String errorMsg;

    @ApiModelProperty("添加日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}