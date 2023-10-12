package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.State;
import com.eghm.handler.mysql.LikeTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 餐饮商家信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("restaurant")
@ApiModel(value = "Restaurant对象", description = "餐饮商家信息表")
public class Restaurant extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商家名称")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String title;

    @ApiModelProperty(value = "所属商户")
    private Long merchantId;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架")
    private State state;

    @ApiModelProperty("商家logo")
    private String logoUrl;

    @ApiModelProperty(value = "商家封面")
    private String coverUrl;

    @ApiModelProperty(value = "营业时间")
    private String openTime;

    @ApiModelProperty(value = "省份")
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    private Long countyId;

    @ApiModelProperty("详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "商家热线")
    private String phone;

    @ApiModelProperty(value = "商家介绍")
    private String introduce;

    @ApiModelProperty("分数")
    private BigDecimal score;

}
