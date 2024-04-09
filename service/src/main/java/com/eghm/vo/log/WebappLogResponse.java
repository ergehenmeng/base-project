package com.eghm.vo.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/7/24
 */
@Data
public class WebappLogResponse {

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("访问链接")
    private String url;

    @ApiModelProperty("访问ip")
    private String ip;

    @ApiModelProperty("访问耗时")
    private Long elapsedTime;

    @ApiModelProperty("注册渠道 PC, ANDROID, IOS, H5, WECHAT, ALIPAY")
    private String channel;

    @ApiModelProperty("请求参数(json)")
    private String requestParam;

    @ApiModelProperty("错误日志")
    private String errorMsg;

    @ApiModelProperty("添加日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("软件版本号 针对app端,例如 1.2.7")
    private String version;

    @ApiModelProperty("客户端平台版本号 ios:10.4.1,android:8.1.0")
    private String osVersion;

    @ApiModelProperty("设备厂商")
    private String deviceBrand;

    @ApiModelProperty("设备型号")
    private String deviceModel;

    @ApiModelProperty("设备唯一编号")
    private String serialNumber;

}
