package com.eghm.domain.operate.model;

import com.eghm.domain.shared.model.BaseEntity;

import com.eghm.domain.shared.enums.FeedbackType;
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

    public void initialize(Long memberId, FeedbackType feedbackType, String version, String systemVersion,
                           String content, String imageUrl, String deviceBrand, String deviceModel) {
        this.memberId = memberId;
        this.feedbackType = feedbackType;
        this.version = version;
        this.systemVersion = systemVersion;
        this.content = content;
        this.imageUrl = imageUrl;
        this.deviceBrand = deviceBrand;
        this.deviceModel = deviceModel;
        this.state = false;
    }

    public void resolve(String remark) {
        this.state = true;
        this.remark = remark;
    }

    public boolean isResolved() {
        return Boolean.TRUE.equals(this.state);
    }
}
