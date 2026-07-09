package com.eghm.infrastructure.persistence.mybatis.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.domain.shared.enums.Channel;
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
public class WebappLogPO {
    /** id主键 */
    private Long id;

    /** 用户id */
    private Long memberId;

    /** 软件版本号 针对app端,例如 1.2.7 */
    private String version;

    /** 注册渠道 PC,ANDROID,IOS,H5,WECHAT,ALIPAY */
    private Channel channel;

    /** 客户端平台版本号 ios: 10.4.1,android:8.1.0 */
    private String osVersion;

    /** 设备厂商 */
    private String deviceBrand;

    /** 设备型号 */
    private String deviceModel;

    /** 设备唯一编号 */
    private String serialNumber;

    /** 访问ip */
    private String ip;

    /** 访问耗时 */
    private Long elapsedTime;

    /** 访问链接 */
    private String url;

    /** 请求参数(json) */
    private String requestParam;

    /** 请求堆栈id */
    private String traceId;

    /** 错误日志 */
    private String errorMsg;

    /** 添加时间 */
    private LocalDateTime createTime;

}
