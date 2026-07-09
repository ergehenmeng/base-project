package com.eghm.dto.operate.feedback;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 手机反馈新增
 *
 * @author 二哥很猛
 * @since 2019/8/28 10:47
 */
@Data
public class FeedbackAddDTO {

    @Schema(description = "反馈内容分类", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer feedbackType;

    @Schema(description = "反馈内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @WordChecker(message = "反馈内容存在敏感字")
    private String content;

    @Schema(description = "图片地址")
    private String imageUrl;

    @Schema(description = "用户id", hidden = true)
    @Assign
    private Long memberId;

    @Schema(description = "设备品牌", hidden = true)
    @Assign
    private String deviceBrand;

    @Schema(description = "设备型号", hidden = true)
    @Assign
    private String deviceModel;

    @Schema(description = "软件版本", hidden = true)
    @Assign
    private String version;

    @Schema(description = "系统版本", hidden = true)
    @Assign
    private String systemVersion;

}
