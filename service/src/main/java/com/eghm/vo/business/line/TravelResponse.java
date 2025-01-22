package com.eghm.vo.business.line;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/6/5
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TravelResponse extends ExcelStyle {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "旅行社名称")
    @ExcelProperty(value = "旅行社名称", index = 0)
    private String title;

    @Schema(description = "详细地址")
    @ExcelProperty(value = "详细地址", index = 1)
    private String detailAddress;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    @ExcelProperty(value = "状态", index = 2, converter = EnumExcelConverter.class)
    private State state;

    @Schema(description = "旅行社电话")
    @ExcelProperty(value = "旅行社电话", index = 3)
    private String phone;

    @Schema(description = "评分")
    @ExcelProperty(value = "评分", index = 4)
    private BigDecimal score;

    @Schema(description = "店铺logo")
    private String logoUrl;

    @Schema(description = "城市id")
    @JsonIgnore
    private Long cityId;

    @Schema(description = "县区id")
    @JsonIgnore
    private Long countyId;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "添加时间", index = 5)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "更新时间", index = 6)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
