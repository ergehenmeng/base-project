package com.eghm.vo.business.line;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 旅行社信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-02-18
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TravelVO {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "旅行社名称")
    private String title;

    @Schema(description = "店铺logo")
    private String logoUrl;

    @Schema(description = "旅行社描述信息")
    private String depict;

    @Schema(description = "旅行社图片")
    private String coverUrl;

    @Schema(description = "评分")
    private BigDecimal score;

    @Schema(description = "状态 0:下架 1:上架 2:强制下架")
    private Integer state;
}
