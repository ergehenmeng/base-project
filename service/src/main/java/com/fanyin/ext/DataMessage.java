package com.fanyin.ext;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户请求信息
 * @author 二哥很猛
 * @date 2018/8/15 13:56
 */
@Data
public class DataMessage implements Serializable {

    private static final long serialVersionUID = 1588228430420375286L;

    /**
     * 软件版本号 针对app端,例如 1.2.7
     */
    private String version;

    /**
     * 客户端类型 ANDROID,IOS,PC,H5
     */
    private String requestType;

    /**
     * 客户端平台版本号 ios: 10.4.1,android:8.1.0
     */
    private String osVersion;

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * token
     */
    private String accessToken;

    /**
     * 用户id
     */
    private Integer userId;

    public DataMessage(String version, String requestType, String osVersion) {
        this.version = version;
        this.requestType = requestType;
        this.osVersion = osVersion;
    }

}
