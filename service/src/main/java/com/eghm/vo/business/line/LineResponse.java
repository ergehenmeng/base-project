package com.eghm.vo.business.line;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.AreaConverter;
import com.eghm.convertor.excel.BooleanExcelConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.Duration;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 线路商品信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LineResponse extends ExcelStyle {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "线路名称")
    @ExcelProperty(value = "线路名称", index = 0)
    private String title;

    @ApiModelProperty(value = "所属旅行社名称")
    @ExcelProperty(value = "旅行社名称", index = 1)
    private String travelAgencyName;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2: 强制下架")
    @ExcelProperty(value = "状态", index = 2, converter = EnumExcelConverter.class)
    private State state;

    @ApiModelProperty(value = "是否为热销商品")
    @ExcelProperty(value = "热销商品", index = 4, converter = BooleanExcelConverter.class)
    private Boolean hotSell;

    @ApiModelProperty(value = "出发省份id")
    @ExcelProperty(value = "出发省份", index = 5, converter = AreaConverter.class)
    private Long startProvinceId;

    @ApiModelProperty(value = "出发城市id")
    @ExcelProperty(value = "出发城市", index = 6, converter = AreaConverter.class)
    private Long startCityId;

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "销售量")
    @ExcelProperty(value = "销售量", index = 7)
    private Integer saleNum;

    @ApiModelProperty(value = "几日游")
    @ExcelProperty(value = "几日游", index = 8, converter = EnumExcelConverter.class)
    private Duration duration;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "添加时间", index = 9)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "添加时间", index = 10)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
