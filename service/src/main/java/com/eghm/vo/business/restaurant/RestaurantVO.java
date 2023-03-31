package com.eghm.vo.business.restaurant;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/1/16
 */

@Data
public class RestaurantVO {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("id主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "商家名称")
    private String title;

    @ApiModelProperty("商家logo")
    private String logoUrl;

    @ApiModelProperty(value = "商家封面")
    private String coverUrl;

    @ApiModelProperty(value = "营业时间")
    private String openTime;

    @ApiModelProperty(value = "省份")
    @JsonIgnore
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    @JsonIgnore
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    @JsonIgnore
    private Long countyId;

    @ApiModelProperty("详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal latitude;

    @ApiModelProperty(value = "商家热线")
    private String phone;

    @ApiModelProperty(value = "商家介绍")
    private String introduce;
}
