package com.eghm.vo.poi;

import com.eghm.convertor.BigDecimalOmitSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * poi线路表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-21
 */
@Data
public class PoiLineResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "线路名称")
    private String title;

    @Schema(description = "状态 0:未上架 1:已上架")
    private Integer state;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "所属区域编号")
    private String areaCode;

    @Schema(description = "所属区域名称")
    private String areaTitle;

    @Schema(description = "预计游玩时间(单位:小时)")
    @JsonSerialize(using = BigDecimalOmitSerializer.class)
    private BigDecimal playTime;

    @Schema(description = "区域经度")
    private BigDecimal longitude;

    @Schema(description = "区域纬度")
    private BigDecimal latitude;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
