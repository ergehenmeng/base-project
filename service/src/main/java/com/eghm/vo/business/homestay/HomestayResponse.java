package com.eghm.vo.business.homestay;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.HomestayLevel;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/12/4
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class HomestayResponse extends ExcelStyle {

    @Schema(description = "民宿ID")
    private Long id;

    @Schema(description = "民宿名称")
    @ExcelProperty(value = "民宿名称", index = 0)
    private String title;

    @Schema(description = "商户名称")
    @ExcelProperty(value = "商户名称", index = 1)
    private String merchantName;

    @Schema(description = "星级 5:五星级 4:四星级 3:三星级 2:二星级 0:其他")
    @ExcelProperty(value = "星级", index = 2, converter = EnumExcelConverter.class)
    private HomestayLevel level;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    @ExcelProperty(value = "状态", index = 3, converter = EnumExcelConverter.class)
    private State state;

    @Schema(description = "封面图片,逗号分隔")
    private String coverUrl;

    @Schema(description = "城市id")
    @JsonIgnore
    private Long cityId;

    @Schema(description = "县区id")
    @JsonIgnore
    private Long countyId;

    @Schema(description = "详细地址")
    @ExcelProperty(value = "详细地址", index = 4)
    private String detailAddress;

    @Schema(description = "联系电话")
    @ExcelProperty(value = "联系电话", index = 5)
    private String phone;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间", index = 6)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "更新时间", index = 7)
    private LocalDateTime updateTime;
}
