package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.Channel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 系统异常记录
 *
 * @author 二哥很猛
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("webapp_log")
public class WebappLog {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "软件版本号 针对app端,例如 1.2.7")
    private String version;

    @Schema(description = "注册渠道 PC,ANDROID,IOS,H5,WECHAT,ALIPAY")
    private Channel channel;

    @Schema(description = "客户端平台版本号 ios: 10.4.1,android:8.1.0")
    private String osVersion;

    @Schema(description = "设备厂商")
    private String deviceBrand;

    @Schema(description = "设备型号")
    private String deviceModel;

    @Schema(description = "设备唯一编号")
    private String serialNumber;

    @Schema(description = "访问ip")
    private String ip;

    @Schema(description = "访问耗时")
    private Long elapsedTime;

    @Schema(description = "访问链接")
    private String url;

    @Schema(description = "请求参数(json)")
    private String requestParam;

    @Schema(description = "请求堆栈id")
    private String traceId;

    @Schema(description = "错误日志")
    private String errorMsg;

    @Schema(description = "添加日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}