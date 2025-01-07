package com.eghm.vo.business.line.config;

import com.eghm.convertor.CentToYuanOmitEncoder;
import com.eghm.vo.business.BaseConfigResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 二哥很猛
 * @since 2022/8/30
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LineConfigResponse extends BaseConfigResponse {

    @Schema(description = "是否可订 0:不可订 1:可定")
    private Boolean state;

    @Schema(description = "划线价")
    @JsonSerialize(using = CentToYuanOmitEncoder.class)
    private Integer linePrice;

    @Schema(description = "销售价")
    @JsonSerialize(using = CentToYuanOmitEncoder.class)
    private Integer salePrice;

    @Schema(description = "库存数")
    private Integer stock;

}
