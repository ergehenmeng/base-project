package com.eghm.vo.business.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/8
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemTagResponse {

    @Schema(description = "主键")
    private String id;

    @Schema(description = "标签名称")
    private String title;

    @Schema(description = "标签图标")
    private String icon;

    @Schema(description = "父节点id")
    private String pid;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态 false:禁用 true:启用")
    private Boolean state;

    @Schema(description = "子节点")
    private List<ItemTagResponse> children;
}
