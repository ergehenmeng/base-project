package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p>
 * 餐饮商家信息表
 * </p>
 * 校验不能删除,上架会使用
 * @author 二哥很猛
 * @since 2022-06-30
 */
@Data
@TableName("restaurant")
@EqualsAndHashCode(callSuper = true)
public class Restaurant extends BaseEntity {

    @ApiModelProperty(value = "商家名称")
    @NotBlank(message = "商家名称不能为空")
    private String title;

    @ApiModelProperty(value = "所属商户")
    private Long merchantId;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @ApiModelProperty("商家logo")
    @NotBlank(message = "logo不能为空")
    private String logoUrl;

    @ApiModelProperty(value = "商家封面")
    @NotBlank(message = "封面图片不能为空")
    private String coverUrl;

    @ApiModelProperty(value = "营业时间")
    @NotBlank(message = "营业时间不能为空")
    private String openTime;

    @ApiModelProperty(value = "省份")
    @NotNull(message = "省份不能为空")
    private Long provinceId;

    @ApiModelProperty(value = "城市id")
    @NotNull(message = "城市不能为空")
    private Long cityId;

    @ApiModelProperty(value = "县区id")
    @NotNull(message = "县区不能为空")
    private Long countyId;

    @ApiModelProperty("详细地址")
    @NotBlank(message = "详细地址不能为空")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    @NotNull(message = "经度不能为空")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    @NotNull(message = "纬度不能为空")
    private BigDecimal latitude;

    @ApiModelProperty(value = "商家热线")
    @NotNull(message = "商家热线不能为空")
    private String phone;

    @ApiModelProperty(value = "商家介绍")
    @NotNull(message = "商家介绍不能为空")
    private String introduce;

    @ApiModelProperty("分数")
    private BigDecimal score;

}
