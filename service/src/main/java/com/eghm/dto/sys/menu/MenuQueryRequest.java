package com.eghm.dto.sys.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/5/27
 */
@Data
public class MenuQueryRequest {

    @Schema(description = "搜索条件")
    private String queryName;

    @Schema(description = "父节点Id")
    private String pid;
}
