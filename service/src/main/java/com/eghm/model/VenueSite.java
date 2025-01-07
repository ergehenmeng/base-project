package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.State;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * <p>
 * 场地信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("venue_site")
public class VenueSite extends BaseEntity {

    @Schema(description = "场地封面图片")
    private String coverUrl;

    @Schema(description = "场地名称")
    private String title;

    @Schema(description = "所属场馆")
    private Long venueId;

    @Schema(description = "所属商户")
    private Long merchantId;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    private State state;

    @Schema(description = "排序 小<->大")
    private Integer sort;

    @Schema(description = "创建日期")
    private LocalDate createDate;

    @Schema(description = "创建月份")
    private String createMonth;
}
