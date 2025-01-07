package com.eghm.vo.business.item.express;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/25
 */
@Data
public class ExpressSelectResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "模板名称")
    private String title;

    @Schema(description = "计费方式 1:按件数 2:按重量")
    private Integer chargeMode;

    @Schema(description = "状态 0:禁用 1:启用")
    private Integer state;
}
