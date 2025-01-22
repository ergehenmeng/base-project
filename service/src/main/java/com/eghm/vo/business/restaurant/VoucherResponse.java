package com.eghm.vo.business.restaurant;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.convertor.excel.CentToYuanConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 餐饮代金券
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-06-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VoucherResponse extends ExcelStyle {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "商品图片")
    private String coverUrl;

    @Schema(description = "商品名称")
    @ExcelProperty(value = "商品名称", index = 0)
    private String title;

    @Schema(description = "所属店铺")
    @ExcelProperty(value = "所属店铺", index = 1)
    private String restaurantName;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    @ExcelProperty(value = "状态", index = 2, converter = EnumExcelConverter.class)
    private State state;

    @Schema(description = "销售价")
    @ExcelProperty(value = "销售价", index = 3, converter = CentToYuanConverter.class)
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer salePrice;

    @Schema(description = "销量")
    @ExcelProperty(value = "销量", index = 4)
    private Integer saleNum;

    @Schema(description = "限购数量")
    @ExcelProperty(value = "限购数量", index = 5)
    private Integer quota;

    @Schema(description = "添加时间")
    @ExcelProperty(value = "添加时间", index = 6)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @ExcelProperty(value = "修改时间", index = 7)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
