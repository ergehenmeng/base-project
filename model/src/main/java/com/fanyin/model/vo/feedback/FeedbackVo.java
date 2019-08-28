package com.fanyin.model.vo.feedback;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2019/8/28 14:01
 */
@Data
public class FeedbackVo implements Serializable {

    private static final long serialVersionUID = -434393367856882980L;

    /**
     * id
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 状态: 0:待解决 1:已解决
     */
    private Byte state;

    /**
     * 状态: 反馈类型分类
     */
    private Byte classify;

    /**
     * 软件版本
     */
    private String version;

    /**
     * 系统版本
     */
    private String systemVersion;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 反馈时间
     */
    private Date addTime;

    /**
     * 处理时间
     */
    private Date updateTime;

    /**
     * 设备厂商
     */
    private String deviceBrand;

    /**
     * 设备型号
     */
    private String deviceModel;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 昵称
     */
    private String nickName;
}
