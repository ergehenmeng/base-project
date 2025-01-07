package com.eghm.vo.poi;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * poi点位信息
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-20
 */
@Data
public class PoiPointResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "点位名称")
    private String title;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "所属类型")
    private String typeTitle;

    @Schema(description = "区域编号")
    private String areaCode;

    @Schema(description = "区域名称")
    private String areaTitle;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "维度")
    private BigDecimal latitude;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
