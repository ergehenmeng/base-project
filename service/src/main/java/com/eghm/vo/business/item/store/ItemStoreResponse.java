package com.eghm.vo.business.item.store;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.BooleanExcelConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 店铺信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ItemStoreResponse extends ExcelStyle {

    @Schema(description = "店铺id")
    private Long id;

    @Schema(description = "店铺logo")
    private String logoUrl;

    @Schema(description = "店铺名称")
    @ExcelProperty(value = "店铺名称", index = 0)
    private String title;

    @Schema(description = "所属商户")
    @ExcelProperty(value = "所属商户", index = 1)
    private String merchantName;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    @ExcelProperty(value = "状态", index = 2, converter = EnumExcelConverter.class)
    private State state;

    @Schema(description = "推荐店铺")
    @ExcelProperty(value = "平台推荐", index = 3, converter = BooleanExcelConverter.class)
    private Boolean recommend;

    @Schema(description = "商家电话")
    @ExcelProperty(value = "商家电话", index = 4)
    private String telephone;

    @Schema(description = "营业时间")
    @ExcelProperty(value = "营业时间", index = 5)
    private String openTime;

    @Schema(description = "评分")
    @ExcelProperty(value = "评分", index = 6)
    private BigDecimal score;

    @Schema(description = "详细地址")
    @ExcelProperty(value = "详细地址", index = 7)
    private String detailAddress;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间", index = 8)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "更新时间", index = 9)
    private LocalDateTime updateTime;

}
