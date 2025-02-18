package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.enums.State;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 景区信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-05-27
 */
@Data
@TableName("scenic")
@EqualsAndHashCode(callSuper = true)
public class Scenic extends BaseEntity {

    @ApiModelProperty(value = "景区名称")
    private String scenicName;

    @ApiModelProperty(value = "景区等级 5: 5A 4: 4A 3: 3A 0:其他")
    private Integer level;

    @ApiModelProperty(value = "景区营业时间")
    private String openTime;

    @ApiModelProperty("景区电话")
    private String phone;

    @ApiModelProperty("景区标签")
    private String tag;

    @ApiModelProperty("景区所属商户id")
    private Long merchantId;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @ApiModelProperty(value = "景区排序(小<->大)")
    private Integer sort;

    @ApiModelProperty(value = "省份id")
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "景区描述信息")
    private String depict;

    @ApiModelProperty(value = "景区图片")
    private String coverUrl;

    @ApiModelProperty(value = "景区详细介绍信息")
    private String introduce;

    @ApiModelProperty(value = "最低票价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer minPrice;

    @ApiModelProperty("最高票价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer maxPrice;

    @ApiModelProperty("景区评分")
    private Integer score;
}
