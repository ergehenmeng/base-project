package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * poi点位信息
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("poi_point")
public class PoiPoint extends BaseEntity {

    @Schema(description = "点位名称")
    private String title;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "所属类型")
    private Long typeId;

    @Schema(description = "区域编号(冗余)")
    private String areaCode;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "维度")
    private BigDecimal latitude;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "详细介绍")
    private String introduce;

}
