package com.eghm.vo.business.line;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "旅行社名称")
    private String title;

    @ApiModelProperty("店铺logo")
    private String logoUrl;

    @ApiModelProperty(value = "旅行社描述信息")
    private String depict;

    @ApiModelProperty(value = "旅行社图片")
    private String coverUrl;

    @ApiModelProperty("评分")
    private BigDecimal score;

    @ApiModelProperty("状态 0:下架 1:上架 2:强制下架")
    private Integer state;
}
