package com.eghm.vo.feedback;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2019/8/28 14:01
 */
@Data
public class FeedbackResponse {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long memberId;

    @ApiModelProperty("状态: false:待解决 true:已解决")
    private Boolean state;

    @ApiModelProperty("状态: 反馈类型分类")
    private Integer feedbackType;

    @ApiModelProperty("软件版本")
    private String version;

    @ApiModelProperty("系统版本")
    private String systemVersion;

    @ApiModelProperty("反馈内容")
    private String content;

    @ApiModelProperty("图片地址")
    private String imageUrl;

    @ApiModelProperty("反馈时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("处理时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("设备厂商")
    private String deviceBrand;

    @ApiModelProperty("设备型号")
    private String deviceModel;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("回复内容")
    private String remark;
}
