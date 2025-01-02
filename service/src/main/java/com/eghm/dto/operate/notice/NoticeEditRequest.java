package com.eghm.dto.operate.notice;

import com.eghm.validation.annotation.WordChecker;
import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2018/11/20 19:17
 */
@Data
public class NoticeEditRequest {

    @Schema(description = "id主键", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "公告标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "标题不能为空")
    @WordChecker(message = "标题存在敏感词")
    private String title;

    @Schema(description = "封面图片", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "封面图片不能为空")
    private String coverUrl;

    @Schema(description = "公告类型(数据字典表sys_notice_type)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "公告类型不能为空")
    private Integer noticeType;

    @Schema(description = "公告内容(富文本)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "公告内容不能为空")
    @WordChecker(message = "公告内容存在敏感词")
    @Expose(serialize = false)
    private String content;

}
