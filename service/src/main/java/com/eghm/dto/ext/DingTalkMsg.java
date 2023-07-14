package com.eghm.dto.ext;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/7/14
 */
@Data
public class DingTalkMsg {

    @JsonProperty("msgtype")
    private String msgType;

    /**
     * 文本消息
     */
    private Text text;

    /**
     * markdown内容
     */
    private Markdown markdown;


    @Data
    public static class Text {

        /**
         * 文本内容
         */
        private String content;
    }

    @Data
    public static class Markdown {

        /**
         * 标题
         */
        private String title;

        /**
         * 内容
         */
        private String text;
    }
}
