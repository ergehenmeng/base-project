package com.eghm.vo.poi;

import com.eghm.convertor.BigDecimalOmitEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "线路名称")
    private String title;

    @ApiModelProperty(value = "封面图")
    private String coverUrl;

    @ApiModelProperty(value = "预计游玩时间")
    @JsonSerialize(using = BigDecimalOmitEncoder.class)
    private BigDecimal playTime;

    @ApiModelProperty("线路详情")
    private String introduce;

    @ApiModelProperty("点位列表")
    private List<PoiPointVO> pointList;
}
