package com.eghm.dao.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("用户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @ApiModelProperty("状态: 0:待解决 1:已解决")
    private Byte state;

    @ApiModelProperty("状态: 反馈类型分类")
    private Byte classify;

    @ApiModelProperty("软件版本")
    private String version;

    @ApiModelProperty("系统版本")
    private String systemVersion;

    @ApiModelProperty("反馈内容")
    private String content;

    @ApiModelProperty("设备类型")
    private String deviceBrand;

    @ApiModelProperty("设备型号")
    private String deviceModel;

}