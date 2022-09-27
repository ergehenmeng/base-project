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

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 店铺信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("product_shop")
@ApiModel(value="ProductShop对象", description="店铺信息表")
public class ProductShop extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("店铺名称")
    private String title;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架")
    private State state;

    @ApiModelProperty(value = "平台状态 0:初始 1:待审核 2:已上架")
    private PlatformState platformState;

    @ApiModelProperty(value = "所属商户id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long merchantId;

    @ApiModelProperty(value = "店铺logo")
    private String logoUrl;

    @ApiModelProperty(value = "营业时间")
    private String openTime;

    @ApiModelProperty(value = "省id")
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
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "商家电话")
    private String telephone;

    @ApiModelProperty(value = "商家介绍")
    private String introduce;

}
