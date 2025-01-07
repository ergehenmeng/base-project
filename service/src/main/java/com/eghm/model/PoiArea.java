package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "区域名称")
    private String title;

    @Schema(description = "状态 0:未上架 1:已上架")
    private Boolean state;

    @Schema(description = "区域编号")
    private String code;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "维度")
    private BigDecimal latitude;

    @Schema(description = "省份id")
    private Long provinceId;

    @Schema(description = "城市id")
    private Long cityId;

    @Schema(description = "区县id")
    private Long countyId;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "区域信息描述")
    private String remark;

}
