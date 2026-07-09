package com.eghm.application.shared.dto.ext;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/7/14
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeiShuMsg {

    @Schema(description = "时间戳")
    private Long timestamp;

    @Schema(description = "签名信息")
    private String sign;

    @Schema(description = "消息类型")
    @JsonProperty("msg_type")
    private String msgType;

    @Schema(description = "文本消息")
    private Content content;

    @Schema(description = "文本消息")
    public record Content(String text) {
    }
}
