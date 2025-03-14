package com.eghm.dto.business.news;

import com.eghm.convertor.JoinerDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/12/29
 */

@Data
public class NewsAddRequest {

    @Schema(description = "资讯标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "标题不能为空")
    @Size(max = 20, message = "标题长度不能超过20")
    private String title;

    @Schema(description = "资讯编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "编码不能为空")
    @Size(max = 20, message = "编码长度不能超过20")
    private String code;

    @Schema(description = "一句话描述信息")
    private String depict;

    @Schema(description = "图集")
    @JsonDeserialize(using = JoinerDeserializer.class)
    private String image;

    @Schema(description = "标签列表")
    @JsonDeserialize(using = JoinerDeserializer.class)
    private String tagName;

    @Schema(description = "详细信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "详细信息不能为空")
    private String content;

    @Schema(description = "视频")
    private String video;

    @Schema(description = "是否支持评论 true:支持 false:不支持")
    @NotNull(message = "是否支持评论不能为空")
    private Boolean commentSupport;

}
