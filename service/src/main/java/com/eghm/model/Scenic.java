package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.PlatformState;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
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
@EqualsAndHashCode(callSuper = false)
@TableName("scenic")
@ApiModel(value="Scenic对象", description="景区信息表")
public class Scenic extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

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
    @JsonSerialize(using = ToStringSerializer.class)
    private Long merchantId;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架")
    private State state;

    @ApiModelProperty(value = "平台状态 0:初始 1:待审核 2:已上架")
    private PlatformState platformState;

    @ApiModelProperty(value = "景区排序(小<->大)")
    private Integer sort;

    @ApiModelProperty(value = "省份id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal latitude;

    @ApiModelProperty(value = "景区描述信息")
    private String depict;

    @ApiModelProperty(value = "景区图片")
    private String coverUrl;

    @ApiModelProperty(value = "景区详细介绍信息")
    private String introduce;

    @ApiModelProperty(value = "最低票价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer minPrice;

    @ApiModelProperty("最高票价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer maxPrice;
}
