package com.eghm.vo.business.item.express;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/24
 */
@Data
public class ExpressTemplateVO {

    @Schema(description = "零售Id")
    private Long itemId;

    @Schema(description = "物流模板Id")
    private Long expressId;

    @Schema(description = "物流计费方式 1:计件 2:计时")
    private Integer chargeMode;
}
