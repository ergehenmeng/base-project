package com.eghm.vo.poi;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/6/25
 */

@Data
public class PoiAreaResponse {

    @Schema(description = "id主键")
    private Long id;

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

    @Schema(description = "省id")
    private Long provinceId;

    @Schema(description = "城市id")
    private Long cityId;

    @Schema(description = "区县id")
    private Long countyId;

    @Schema(description = "详细地址")
    private String detailAddress;

    @Schema(description = "格式化后的地址")
    private String parsedAddress;

    @Schema(description = "区域信息描述")
    private String remark;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
