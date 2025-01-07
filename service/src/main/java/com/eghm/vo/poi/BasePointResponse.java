package com.eghm.vo.poi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/12/22
 */

@Data
public class BasePointResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "点位名称")
    private String title;

    @Schema(description = "所属类型")
    private String typeTitle;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "维度")
    private BigDecimal latitude;
}
