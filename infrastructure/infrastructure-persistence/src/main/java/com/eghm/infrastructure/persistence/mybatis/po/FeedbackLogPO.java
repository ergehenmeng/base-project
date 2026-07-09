package com.eghm.infrastructure.persistence.mybatis.po;

import com.eghm.domain.shared.enums.FeedbackType;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@TableName("feedback_log")
@EqualsAndHashCode(callSuper = true)
public class FeedbackLogPO extends BaseEntityPO {

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


