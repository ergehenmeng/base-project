package com.eghm.vo.business.scenic;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.convertor.excel.CentToYuanConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ScenicLevel;
import com.eghm.enums.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "景区名称")
    @ExcelProperty(value = "景区名称", index = 0)
    private String scenicName;

    @ApiModelProperty(value = "景区等级 5: 5A 4: 4A 3: 3A 0:无")
    @ExcelProperty(value = "景区等级", index = 1, converter = EnumExcelConverter.class)
    private ScenicLevel level;

    @ApiModelProperty(value = "开放时间")
    @ExcelProperty(value = "开放时间", index = 2)
    private String openTime;

    @ApiModelProperty("景区电话")
    @ExcelProperty(value = "电话", index = 3)
    private String phone;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    @ExcelProperty(value = "状态", index = 4, converter = EnumExcelConverter.class)
    private State state;

    @ApiModelProperty("评分")
    @ExcelProperty(value = "评分", index = 5)
    private Integer score;

    @ApiModelProperty(value = "最低票价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    @ExcelProperty(value = "票价", index = 6, converter = CentToYuanConverter.class)
    private Integer minPrice;

    @ApiModelProperty("最高票价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    @ExcelProperty(value = "票价", index = 7, converter = CentToYuanConverter.class)
    private Integer maxPrice;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "添加时间", index = 8)
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "更新时间", index = 9)
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
