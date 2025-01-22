package com.eghm.vo.business.line;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.Duration;
import com.eghm.enums.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "线路名称")
    @ExcelProperty(value = "线路名称", index = 0)
    private String title;

    @Schema(description = "所属旅行社名称")
    @ExcelProperty(value = "旅行社名称", index = 1)
    private String travelAgencyName;

    @Schema(description = "出发城市")
    @ExcelProperty(value = "出发城市", index = 2)
    private String startCity;

    @Schema(description = "状态 0:待上架 1:已上架 2: 强制下架")
    @ExcelProperty(value = "状态", index = 3, converter = EnumExcelConverter.class)
    private State state;

    @Schema(description = "销售量")
    @ExcelProperty(value = "真实销量", index = 4)
    private Integer saleNum;

    @Schema(description = "游玩天数 1:一日游 2:二日游 3:三日游 4:四日游 5:五日游 6:六日游 7:七日游 8:八日游 9:九日游 10:十日游 11:十一日游 12:十二日游 13:十三日游 14:十四日游 15:十五日游")
    @ExcelProperty(value = "游玩天数", index = 5, converter = EnumExcelConverter.class)
    private Duration duration;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "添加时间", index = 6)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "更新时间", index = 7)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "出发省份id")
    @JsonIgnore
    private Long startProvinceId;

    @Schema(description = "出发城市id")
    @JsonIgnore
    private Long startCityId;

}
