package com.eghm.dto.business.news;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/12/29
 */

@Data
public class NewsEditRequest {

    @Schema(description = "资讯id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "资讯id不能为空")
    private Long id;

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

    @Schema(description = "标签列表")
    @Size(max = 3, message = "标签不能超过3个")
    private List<String> tagList;

    @Schema(description = "图集")
    private List<String> imageList;

    @Schema(description = "详细信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "详细信息不能为空")
    private String content;

    @Schema(description = "视频")
    private String video;

    @Schema(description = "是否支持评论 true:支持 false:不支持")
    @NotNull(message = "是否支持评论不能为空")
    private Boolean commentSupport;

}
