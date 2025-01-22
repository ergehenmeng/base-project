package com.eghm.vo.business.line;

import com.eghm.enums.State;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/6/5
 */

@Data
public class BaseTravelResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "旅行社名称")
    private String title;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

}
