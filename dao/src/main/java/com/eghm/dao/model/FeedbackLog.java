package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class FeedbackLog extends BaseEntity {

    /**
     * 用户ID<br>
     * 表 : feedback_log<br>
     * 对应字段 : user_id<br>
     */
    private Long userId;

    /**
     * 状态: 0:待解决 1:已解决<br>
     * 表 : feedback_log<br>
     * 对应字段 : state<br>
     */
    private Byte state;

    /**
     * 状态: 反馈类型分类<br>
     * 表 : feedback_log<br>
     * 对应字段 : classify<br>
     */
    private Byte classify;

    /**
     * 软件版本<br>
     * 表 : feedback_log<br>
     * 对应字段 : version<br>
     */
    private String version;

    /**
     * 系统版本<br>
     * 表 : feedback_log<br>
     * 对应字段 : system_version<br>
     */
    private String systemVersion;

    /**
     * 反馈内容<br>
     * 表 : feedback_log<br>
     * 对应字段 : content<br>
     */
    private String content;

    /**
     * 设备类型<br>
     * 表 : feedback_log<br>
     * 对应字段 : device_brand<br>
     */
    private String deviceBrand;

    /**
     * <br>
     * 表 : feedback_log<br>
     * 对应字段 : device_model<br>
     */
    private String deviceModel;

}