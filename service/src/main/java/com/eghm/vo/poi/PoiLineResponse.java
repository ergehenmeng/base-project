package com.eghm.vo.poi;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * poi线路表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-21
 */
@Data
public class PoiLineResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "线路名称")
    private String title;

    @ApiModelProperty(value = "封面图")
    private String coverUrl;

    @ApiModelProperty(value = "所属区域编号")
    private String areaCode;

    @ApiModelProperty(value = "所属区域名称")
    private String areaTitle;

    @ApiModelProperty(value = "预计游玩时间")
    private BigDecimal playTime;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
