package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * poi区域表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("poi_area")
public class PoiArea extends BaseEntity {

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

    @ApiModelProperty(value = "省份id")
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    private Long cityId;

    @ApiModelProperty(value = "区县id")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "区域信息描述")
    private String remark;

}
