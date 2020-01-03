package com.eghm.model.ext;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登陆信息记录
 * @author 二哥很猛
 * @date 2019/8/28 15:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRecord implements Serializable {

    private static final long serialVersionUID = -8379974755063972428L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * ip
     */
    private String ip;

    /**
     * 登陆渠道
     */
    private String channel;

    /**
     * 设备厂商
     */
    private String deviceBrand;

    /**
     * 设备型号
     */
    private String deviceModel;

    /**
     * 软件版本
     */
    private String softwareVersion;
}
