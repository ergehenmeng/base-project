package com.eghm.vo.poi;

import com.eghm.convertor.BigDecimalOmitEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * poi线路表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-21
 */
@Data
public class PoiLineVO {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "线路名称")
    private String title;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "预计游玩时间")
    @JsonSerialize(using = BigDecimalOmitEncoder.class)
    private BigDecimal playTime;

    @Schema(description = "线路详情")
    private String introduce;

    @Schema(description = "点位列表")
    private List<PoiPointVO> pointList;
}
