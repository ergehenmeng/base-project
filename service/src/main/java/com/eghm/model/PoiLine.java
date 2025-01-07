package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * poi线路表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("poi_line")
public class PoiLine extends BaseEntity {

    @Schema(description = "线路名称")
    private String title;

    @Schema(description = "状态 0:未上架 1:已上架")
    private Integer state;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "所属区域编号")
    private String areaCode;

    @Schema(description = "详细介绍")
    private String introduce;

    @Schema(description = "预计游玩时间(单位:小时)")
    private BigDecimal playTime;

}
