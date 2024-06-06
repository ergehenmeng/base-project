package com.eghm.vo.business.line;

import com.eghm.enums.ref.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/6/5
 */

@Data
public class TravelResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "旅行社名称")
    private String title;

    @ApiModelProperty("店铺logo")
    private String logoUrl;

    @ApiModelProperty("旅行社电话")
    private String phone;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @ApiModelProperty(value = "城市id")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "旅行社图片")
    private String coverUrl;

    @ApiModelProperty("评分")
    private BigDecimal score;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
