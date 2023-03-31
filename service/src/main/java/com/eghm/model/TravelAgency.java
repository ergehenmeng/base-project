package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.PlatformState;
import com.eghm.enums.ref.State;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("travel_agency")
@ApiModel(value="TravelAgency对象", description="旅行社信息表")
public class TravelAgency extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "旅行社名称")
    private String title;

    @ApiModelProperty(value = "旅行社所属商户")
    private Long merchantId;

    @ApiModelProperty(value = "状态 -1:初始化 0:待上架 1:已上架")
    private State state;

    @ApiModelProperty(value = "审核状态 0:初始  1:未上架 2:已上架")
    private PlatformState platformState;

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
