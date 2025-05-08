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
public class ItemTagVO {

    @Schema(description = "主键")
    private String id;

    @Schema(description = "标签名称")
    private String title;

    @Schema(description = "标签图标")
    private String icon;

    @Schema(description = "子节点")
    private List<ItemTagVO> children;
}
