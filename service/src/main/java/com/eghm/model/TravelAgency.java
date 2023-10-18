package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.State;
import com.eghm.handler.mysql.LikeTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 旅行社信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-02-18
 */
@Data
@TableName("travel_agency")
@EqualsAndHashCode(callSuper = true)
public class TravelAgency extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "旅行社名称")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String title;

    @ApiModelProperty(value = "旅行社所属商户")
    private Long merchantId;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

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

    @ApiModelProperty(value = "旅行社描述信息")
    private String depict;

    @ApiModelProperty(value = "旅行社图片")
    private String coverUrl;

    @ApiModelProperty(value = "旅行社详细介绍信息")
    private String introduce;

}
