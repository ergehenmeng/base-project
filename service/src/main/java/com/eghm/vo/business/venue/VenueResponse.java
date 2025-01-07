package com.eghm.vo.business.venue;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.State;
import com.eghm.enums.ref.VenueType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/2/2
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class VenueResponse extends ExcelStyle {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "场馆封面图")
    private String coverUrl;

    @Schema(description = "市")
    @JsonIgnore
    private Long cityId;

    @Schema(description = "区县")
    @JsonIgnore
    private Long countyId;

    @Schema(description = "场馆名称")
    @ExcelProperty(value = "场馆名称", index = 0)
    private String title;

    @Schema(description = "场馆类型 (1:篮球馆 2:网球馆 3:羽毛球馆 4:乒乓球馆 5:游泳馆 6:健身馆 7:瑜伽馆 8:保龄馆 9:足球馆 10:排球馆 11:田径馆 12:综合馆 13:跆拳道馆)")
    @ExcelProperty(value = "场馆类型", index = 1, converter = EnumExcelConverter.class)
    private VenueType venueType;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    @ExcelProperty(value = "状态", index = 2, converter = EnumExcelConverter.class)
    private State state;

    @Schema(description = "营业时间")
    @ExcelProperty(value = "营业时间", index = 3)
    private String openTime;

    @Schema(description = "商家电话")
    @ExcelProperty(value = "商家电话", index = 4)
    private String telephone;

    @Schema(description = "详细地址")
    @ExcelProperty(value = "详细地址", index = 5)
    private String detailAddress;

    @Schema(description = "添加时间")
    @ExcelProperty(value = "添加时间", index = 6)
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @ExcelProperty(value = "更新时间", index = 7)
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
