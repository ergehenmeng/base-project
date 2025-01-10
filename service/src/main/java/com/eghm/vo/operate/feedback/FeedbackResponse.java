package com.eghm.vo.operate.feedback;

import com.eghm.enums.FeedbackType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2019/8/28 14:01
 */
@Data
public class FeedbackResponse {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "状态: false:待解决 true:已解决")
    private Boolean state;

    @Schema(description = "反馈类型分类")
    private FeedbackType feedbackType;

    @Schema(description = "软件版本")
    private String version;

    @Schema(description = "系统版本")
    private String systemVersion;

    @Schema(description = "反馈内容")
    private String content;

    @Schema(description = "图片地址")
    private String imageUrl;

    @Schema(description = "反馈时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "处理时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "设备厂商")
    private String deviceBrand;

    @Schema(description = "设备型号")
    private String deviceModel;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "回复内容")
    private String remark;
}
