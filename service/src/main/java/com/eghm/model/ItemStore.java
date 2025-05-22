package com.eghm.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.SplitterArraySerializer;
import com.eghm.enums.State;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 店铺信息表
 * </p>
 * 校验不能删除,上架会使用
 * @author 二哥很猛
 * @since 2022-07-01
 */
@Data
@TableName("item_store")
@EqualsAndHashCode(callSuper = true)
public class ItemStore extends BaseEntity {

    @Schema(description = "店铺名称")
    private String title;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @Schema(description = "所属商户id")
    private Long merchantId;

    @Schema(description = "店铺logo")
    private String logoUrl;

    @Schema(description = "封面图")
    @JsonSerialize(using = SplitterArraySerializer.class)
    private String coverUrl;

    @Schema(description = "营业时间")
    private String openTime;

    @Schema(description = "省id")
    private Long provinceId;

    @Schema(description = "城市id")
    private Long cityId;

    @Schema(description = "县区id")
    private Long countyId;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    private BigDecimal latitude;

    @Schema(description = "商家电话")
    private String telephone;

    @Schema(description = "商家介绍")
    private String introduce;

    @Schema(description = "是否为推荐店铺 true:是 false:不是")
    private Boolean recommend;

    @Schema(description = "评分")
    private BigDecimal score;

    @Schema(description = "仓库地址id")
    private Long depotAddressId;

    @Schema(description = "自提点")
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private Long pickupId;
}
