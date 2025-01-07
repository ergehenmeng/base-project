package com.eghm.vo.business.scenic;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.convertor.excel.CentToYuanConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.ScenicLevel;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/6/3
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class ScenicResponse extends ExcelStyle {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "景区名称")
    @ExcelProperty(value = "景区名称", index = 0)
    private String scenicName;

    @Schema(description = "景区等级 5: 5A 4: 4A 3: 3A 0:无")
    @ExcelProperty(value = "景区等级", index = 1, converter = EnumExcelConverter.class)
    private ScenicLevel level;

    @Schema(description = "开放时间")
    @ExcelProperty(value = "开放时间", index = 2)
    private String openTime;

    @Schema(description = "景区电话")
    @ExcelProperty(value = "电话", index = 3)
    private String phone;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    @ExcelProperty(value = "状态", index = 4, converter = EnumExcelConverter.class)
    private State state;

    @Schema(description = "评分")
    @ExcelProperty(value = "评分", index = 5)
    private Integer score;

    @Schema(description = "最低票价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    @ExcelProperty(value = "票价", index = 6, converter = CentToYuanConverter.class)
    private Integer minPrice;

    @Schema(description = "最高票价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    @ExcelProperty(value = "票价", index = 7, converter = CentToYuanConverter.class)
    private Integer maxPrice;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "添加时间", index = 8)
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "更新时间", index = 9)
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
