package com.eghm.dao.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 */
@Data
public class FeedbackLog implements Serializable {

    private static final long serialVersionUID = 8825037006949192369L;

    /**
     * <br>
     * 表 : feedback_log<br>
     * 对应字段 : id<br>
     */
    private Long id;

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
     * 反馈时间<br>
     * 表 : feedback_log<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : feedback_log<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

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