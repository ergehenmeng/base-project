package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("item_store")
@EqualsAndHashCode(callSuper = true)
public class ItemStore extends BaseEntity {

    @ApiModelProperty("店铺名称")
    private String title;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @ApiModelProperty(value = "所属商户id")
    private Long merchantId;

    @ApiModelProperty(value = "店铺logo")
    private String logoUrl;

    @ApiModelProperty("封面图")
    private String coverUrl;

    @ApiModelProperty(value = "营业时间")
    private String openTime;

    @ApiModelProperty(value = "省id")
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

    @ApiModelProperty(value = "商家电话")
    private String telephone;

    @ApiModelProperty(value = "商家介绍")
    private String introduce;

    @ApiModelProperty("是否为推荐店铺 true:是 false:不是")
    private Boolean recommend;

    @ApiModelProperty("评分")
    private BigDecimal score;

    @ApiModelProperty("仓库地址id")
    private Long depotAddressId;
}
