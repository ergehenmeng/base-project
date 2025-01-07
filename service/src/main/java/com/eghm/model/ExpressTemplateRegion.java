package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 快递模板区域
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-22
 */
@Data
@TableName("express_template_region")
@EqualsAndHashCode(callSuper = true)
public class ExpressTemplateRegion extends BaseEntity {

    @Schema(description = "模板id")
    private Long expressId;

    @Schema(description = "首件或首重")
    private BigDecimal firstPart;

    @Schema(description = "首件或首重的价格")
    private Integer firstPrice;

    @Schema(description = "续重或续件")
    private BigDecimal nextPart;

    @Schema(description = "续重或续件的单价")
    private Integer nextUnitPrice;

    @Schema(description = "区域编号(逗号分隔)")
    private String regionCode;

    @Schema(description = "区域名称(逗号分隔)")
    private String regionName;

}
