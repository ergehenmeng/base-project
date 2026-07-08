package com.eghm.operate.model;

import com.eghm.common.model.BaseEntity;

import com.eghm.enums.FeedbackType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FeedbackLog extends BaseEntity {

    /** 用户ID */
    private Long memberId;

    /** 状态: 0:待解决 1:已解决 */
    private Boolean state;

    /** 反馈类型 */
    private FeedbackType feedbackType;

    /** 软件版本 */
    private String version;

    /** 系统版本 */
    private String systemVersion;

    /** 反馈内容 */
    private String content;

    /** 图片url */
    private String imageUrl;

    /** 设备类型 */
    private String deviceBrand;

    /** 设备型号 */
    private String deviceModel;

    /** 回复内容 */
    private String remark;
}
