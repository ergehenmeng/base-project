package com.fanyin.model.dto.business.feedback;

import lombok.Data;

import java.io.Serializable;

/**
 * 手机反馈新增
 * @author 二哥很猛
 * @date 2019/8/28 10:47
 */
@Data
public class FeedbackAddRequest implements Serializable {

    private static final long serialVersionUID = -1472450823258477249L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 软件版本
     */
    private String version;

    /**
     * 系统版本
     */
    private String systemVersion;

    /**
     * 反馈分类
     */
    private Byte classify;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 设备品牌
     */
    private String deviceBrand;

    /**
     * 设备型号
     */
    private String deviceModel;
}
