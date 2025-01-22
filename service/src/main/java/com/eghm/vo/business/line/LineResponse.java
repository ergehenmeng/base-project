package com.eghm.vo.business.line;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.Duration;
import com.eghm.enums.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ApiModelProperty(value = "封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "线路名称")
    @ExcelProperty(value = "线路名称", index = 0)
    private String title;

    @ApiModelProperty(value = "所属旅行社名称")
    @ExcelProperty(value = "旅行社名称", index = 1)
    private String travelAgencyName;

    @ApiModelProperty(value = "出发城市")
    @ExcelProperty(value = "出发城市", index = 2)
    private String startCity;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2: 强制下架")
    @ExcelProperty(value = "状态", index = 3, converter = EnumExcelConverter.class)
    private State state;

    @ApiModelProperty(value = "销售量")
    @ExcelProperty(value = "真实销量", index = 4)
    private Integer saleNum;

    @ApiModelProperty(value = "游玩天数")
    @ExcelProperty(value = "游玩天数", index = 5, converter = EnumExcelConverter.class)
    private Duration duration;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "添加时间", index = 6)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "更新时间", index = 7)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "出发省份id")
    @JsonIgnore
    private Long startProvinceId;

    @ApiModelProperty(value = "出发城市id")
    @JsonIgnore
    private Long startCityId;

}
