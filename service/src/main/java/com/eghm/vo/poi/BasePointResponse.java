package com.eghm.vo.poi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/12/22
 */

@Data
public class BasePointResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "封面图")
    private String coverUrl;

    @ApiModelProperty(value = "点位名称")
    private String title;

    @ApiModelProperty(value = "所属类型")
    private String typeTitle;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "维度")
    private BigDecimal latitude;
}
