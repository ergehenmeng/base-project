package com.eghm.model.ext;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户请求信息
 * @author 二哥很猛
 * @date 2018/8/15 13:56
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestMessage implements Serializable {

    private static final long serialVersionUID = 1588228430420375286L;

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
     * signKey 验签使用,由后台设置进去
     */
    private String signKey;

    /**
     * token 用户登陆状态保持
     */
    private String accessToken;

    /**
     * 用户id
     */
    private int userId;


    /**
     * http请求内容,json格式
     */
    private String requestBody;
}
