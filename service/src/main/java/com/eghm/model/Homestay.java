package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 民宿信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("homestay")
@ApiModel(value="Homestay对象", description="民宿信息表")
public class Homestay extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "民宿名称")
    private String title;

    @ApiModelProperty(value = "民宿所属商家")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long merchantId;

    @ApiModelProperty(value = "星级 5:五星级 4:四星级 3:三星级 0: 其他")
    private Integer level;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架")
    private State state;

    @ApiModelProperty(value = "平台状态 0:初始 1:待审核 2:已上架")
    private PlatformState platformState;

    @ApiModelProperty(value = "省份")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long provinceId;

    @ApiModelProperty(value = "城市")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long cityId;

    @ApiModelProperty(value = "县区")
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

    @ApiModelProperty(value = "描述信息")
    private String intro;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "详细介绍")
    private String introduce;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "基础设施")
    private String infrastructure;

    @ApiModelProperty(value = "卫浴设施")
    private String bathroom;

    @ApiModelProperty(value = "厨房设施")
    private String kitchen;

    @ApiModelProperty(value = "特色服务")
    private String keyService;

    @ApiModelProperty(value = "标签")
    private String tag;
}
