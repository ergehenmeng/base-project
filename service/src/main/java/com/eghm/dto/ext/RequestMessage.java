package com.eghm.dto.ext;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户请求信息
 *
 * @author 二哥很猛
 * @since 2018/8/15 13:56
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestMessage {

    /**
     * 软件版本号 针对app端,例如 1.2.7
     */
    private String version;

    /**
     * 客户端类型 ANDROID,IOS,PC,H5
     */
    private String channel;

    /**
     * 客户端平台版本号 ios: 10.4.1,android:8.1.0
     */
    private String osVersion;

    /**
     * 设备厂商
     */
    private String deviceBrand;

    /**
     * 设备型号
     */
    private String deviceModel;

    /**
     * 设备唯一编号
     */
    private String serialNumber;

    /**
     * 用户id
     */
    private Long memberId;

    /**
     * appKey(第三方)
     */
    private String appKey;

    /**
     * 签名信息(第三方)
     */
    private String signature;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * http请求内容,json格式
     */
    private String requestParam;

}
