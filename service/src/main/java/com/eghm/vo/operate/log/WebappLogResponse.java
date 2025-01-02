package com.eghm.vo.operate.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/7/24
 */
@Data
public class WebappLogResponse {

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "访问链接")
    private String url;

    @Schema(description = "访问ip")
    private String ip;

    @Schema(description = "访问耗时")
    private Long elapsedTime;

    @Schema(description = "注册渠道 PC, ANDROID, IOS, H5, WECHAT, ALIPAY")
    private String channel;

    @Schema(description = "请求参数(json)")
    private String requestParam;

    @Schema(description = "错误日志")
    private String errorMsg;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "软件版本号 针对app端,例如 1.2.7")
    private String version;

    @Schema(description = "客户端平台版本号 ios:10.4.1,android:8.1.0")
    private String osVersion;

    @Schema(description = "设备厂商")
    private String deviceBrand;

    @Schema(description = "设备型号")
    private String deviceModel;

    @Schema(description = "设备唯一编号")
    private String serialNumber;

}
