package com.eghm.dto.business.member;

import com.eghm.enums.MessageType;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/3/6
 */

@Data
public class SendNotifyRequest {

    @Schema(description = "消息名称")
    @NotBlank(message = "消息名称不能为空")
    @Size(min = 2, max = 20, message = "消息名称长度2~20位")
    @WordChecker(message = "消息名称存在敏感词")
    private String title;

    @Schema(description = "内容")
    @NotBlank(message = "内容不能为空")
    @Size(min = 10, max = 200, message = "内容长度10~200位")
    @WordChecker(message = "内容存在敏感词")
    private String content;

    @Schema(description = "通知类型 common:通用, marketing:营销, feedback_process:反馈处理, evaluation_refuse:订单评价内容不合规")
    @NotNull(message = "通知类型不能为空")
    private MessageType messageType;

    @Schema(description = "会员id")
    @NotNull(message = "请选择会员")
    private List<Long> memberIds;

}
