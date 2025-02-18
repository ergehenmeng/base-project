package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p>
 * 旅行社信息表
 * </p>
 * 校验不能删除,上架会使用
 * @author 二哥很猛
 * @since 2023-02-18
 */
@Data
@TableName("travel_agency")
@EqualsAndHashCode(callSuper = true)
public class TravelAgency extends BaseEntity {

    @ApiModelProperty(value = "旅行社名称")
    @NotBlank(message = "旅行社名称不能为空")
    private String title;

    @ApiModelProperty(value = "旅行社所属商户")
    private Long merchantId;

    @ApiModelProperty("店铺logo")
    @NotBlank(message = "店铺logo不能能为空")
    private String logoUrl;

    @ApiModelProperty("旅行社电话")
    private String phone;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @ApiModelProperty(value = "省份id")
    @NotNull(message = "请选择省份")
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    @NotNull(message = "请选择城市")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    @NotNull(message = "请选择县区")
    private Long countyId;

    @ApiModelProperty(value = "详细地址")
    @NotBlank(message = "详细地址不能为空")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    @NotNull(message = "经度不能能为空")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    @NotNull(message = "维度不能为空")
    private BigDecimal latitude;

    @ApiModelProperty(value = "旅行社描述信息")
    @NotBlank(message = "描述信息不能为空")
    private String depict;

    @ApiModelProperty(value = "旅行社图片")
    @NotBlank(message = "旅行社图片不能为空")
    private String coverUrl;

    @ApiModelProperty(value = "旅行社详细介绍信息")
    @NotBlank(message = "旅行社详细介绍不能为空")
    private String introduce;

    @ApiModelProperty("店铺评分")
    private BigDecimal score;

}
