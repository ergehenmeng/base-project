package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.EnumDescSerializer;
import com.eghm.enums.FeedbackType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@TableName("feedback_log")
@EqualsAndHashCode(callSuper = true)
public class FeedbackLog extends BaseEntity {

    @Schema(description = "用户ID")
    private Long memberId;

    @Schema(description = "状态: 0:待解决 1:已解决")
    private Boolean state;

    @Schema(description = "反馈类型")
    @JsonSerialize(using = EnumDescSerializer.class)
    private FeedbackType feedbackType;

    @Schema(description = "软件版本")
    private String version;

    @Schema(description = "系统版本")
    private String systemVersion;

    @Schema(description = "反馈内容")
    private String content;

    @Schema(description = "图片url")
    private String imageUrl;

    @Schema(description = "设备类型")
    private String deviceBrand;

    @Schema(description = "设备型号")
    private String deviceModel;

    @Schema(description = "回复内容")
    private String remark;
}