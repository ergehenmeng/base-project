package com.eghm.application.shared.vo.operate.template;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 站内信模板响应
 *
 * @author 二哥很猛
 */
@Data
public class NoticeTemplateResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "消息模板code")
    private String code;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "通知内容")
    private String content;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
