package com.eghm.vo.poi;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * poi点位信息
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-20
 */
@Data
public class PoiPointResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "点位名称")
    private String title;

    @ApiModelProperty(value = "封面图")
    private String coverUrl;

    @ApiModelProperty(value = "所属类型")
    private String typeTitle;

    @ApiModelProperty(value = "区域编号")
    private String areaCode;

    @ApiModelProperty(value = "区域名称")
    private String areaTitle;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "维度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
