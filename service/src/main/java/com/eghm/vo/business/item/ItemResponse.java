package com.eghm.vo.business.item;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.convertor.excel.BooleanExcelConverter;
import com.eghm.convertor.excel.CentToYuanConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2022/12/30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ItemResponse extends ExcelStyle {

    @Schema(description = "商品id")
    private Long id;

    @Schema(description = "所属店铺")
    private Long storeId;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "商品名称")
    @ExcelProperty(value = "商品名称", index = 0)
    private String title;

    @Schema(description = "店铺名称")
    @ExcelProperty(value = "店铺名称", index = 1)
    private String storeName;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    @ExcelProperty(value = "状态", index = 2, converter = EnumExcelConverter.class)
    private State state;

    @Schema(description = "限购数量")
    @ExcelProperty(value = "限购数量", index = 3)
    private Integer quota;

    @Schema(description = "交付方式 1:门店自提 2:快递")
    @ExcelProperty(value = "交付方式", index = 4, converter = EnumExcelConverter.class)
    private DeliveryType deliveryType;

    @Schema(description = "最低价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    @ExcelProperty(value = "最低价格", index = 5, converter = CentToYuanConverter.class)
    private Integer minPrice;

    @Schema(description = "最高价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    @ExcelProperty(value = "最高价格", index = 6, converter = CentToYuanConverter.class)
    private Integer maxPrice;

    @Schema(description = "销售数量(所有规格销售总量)")
    @ExcelProperty(value = "销量", index = 7)
    private Integer saleNum;

    @Schema(description = "排序")
    @ExcelProperty(value = "排序", index = 8)
    private Integer sort;

    @Schema(description = "平台推荐")
    @ExcelProperty(value = "平台推荐", index = 9, converter = BooleanExcelConverter.class)
    private Boolean recommend;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "添加时间", index = 10)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "更新时间", index = 11)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
