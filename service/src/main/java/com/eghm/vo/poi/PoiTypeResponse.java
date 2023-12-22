package com.eghm.vo.poi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * poi类型表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-20
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PoiTypeResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "poi类型名称")
    private String title;

    @ApiModelProperty(value = "区域编号")
    private String areaCode;

    @ApiModelProperty(value = "区域名称")
    private String areaTitle;

    @ApiModelProperty(value = "点位类型icon")
    private String icon;

    @ApiModelProperty(value = "点位排序 小<->大")
    private Integer sort;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
