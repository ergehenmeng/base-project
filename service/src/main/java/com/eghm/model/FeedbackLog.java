package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 */
@Data
@TableName("feedback_log")
@EqualsAndHashCode(callSuper = true)
public class FeedbackLog extends BaseEntity {

    @ApiModelProperty("用户ID")
    private Long memberId;

    @ApiModelProperty("状态: 0:待解决 1:已解决")
    private Boolean state;

    @ApiModelProperty("状态: 反馈类型分类")
    private Integer classify;

    @ApiModelProperty("软件版本")
    private String version;

    @ApiModelProperty("系统版本")
    private String systemVersion;

    @ApiModelProperty("反馈内容")
    private String content;

    @ApiModelProperty("图片url")
    private String imageUrl;

    @ApiModelProperty("设备类型")
    private String deviceBrand;

    @ApiModelProperty("设备型号")
    private String deviceModel;

    @ApiModelProperty("回复内容")
    private String remark;
}