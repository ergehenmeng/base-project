package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 景区信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-05-27
 */
@Data
@TableName("scenic")
@EqualsAndHashCode(callSuper = true)
public class Scenic extends BaseEntity {

    @Schema(description = "景区名称")
    private String scenicName;

    @Schema(description = "景区等级 5: 5A 4: 4A 3: 3A 0:其他")
    private Integer level;

    @Schema(description = "景区营业时间")
    private String openTime;

    @Schema(description = "景区电话")
    private String phone;

    @Schema(description = "景区标签")
    private String tag;

    @Schema(description = "景区所属商户id")
    private Long merchantId;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @Schema(description = "景区排序(小<->大)")
    private Integer sort;

    @Schema(description = "省份id")
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

    @Schema(description = "景区描述信息")
    private String depict;

    @Schema(description = "景区图片")
    private String coverUrl;

    @Schema(description = "景区详细介绍信息")
    private String introduce;

    @Schema(description = "最低票价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer minPrice;

    @Schema(description = "最高票价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer maxPrice;

    @Schema(description = "景区评分")
    private Integer score;
}
