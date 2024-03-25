package com.eghm.dto.ext;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/7/14
 */
@Data
public class DingTalkMsg {

    @ApiModelProperty("消息类型")
    @JsonProperty("msgtype")
    private String msgType;

    @ApiModelProperty("文本消息")
    private Text text;

    @Data
    public static class Text {

        @ApiModelProperty("内容")
        private String content;
    }
}
