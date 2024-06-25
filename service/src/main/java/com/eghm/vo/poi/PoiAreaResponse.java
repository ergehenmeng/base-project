package com.eghm.vo.poi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/6/25
 */

@Data
public class PoiAreaResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "区域名称")
    private String title;

    @ApiModelProperty(value = "状态 0:未上架 1:已上架")
    private Boolean state;

    @ApiModelProperty(value = "区域编号")
    private String code;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "维度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "城市id")
    @JsonIgnore
    private Long cityId;

    @ApiModelProperty(value = "区县id")
    @JsonIgnore
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "区域信息描述")
    private String remark;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
