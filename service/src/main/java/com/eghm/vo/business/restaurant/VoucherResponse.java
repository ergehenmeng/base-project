package com.eghm.vo.business.restaurant;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.convertor.excel.BooleanExcelConverter;
import com.eghm.convertor.excel.CentToYuanConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "商品图片")
    private String coverUrl;

    @ApiModelProperty(value = "商品名称")
    @ExcelProperty(value = "商品名称", index = 0)
    private String title;

    @ApiModelProperty(value = "所属店铺")
    @ExcelProperty(value = "所属店铺", index = 1)
    private String restaurantName;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    @ExcelProperty(value = "状态", index = 2, converter = EnumExcelConverter.class)
    private State state;

    @ApiModelProperty(value = "销售价")
    @ExcelProperty(value = "销售价", index = 3, converter = CentToYuanConverter.class)
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @ApiModelProperty(value = "销量")
    @ExcelProperty(value = "销量", index = 4)
    private Integer saleNum;

    @ApiModelProperty(value = "限购数量")
    @ExcelProperty(value = "限购数量", index = 5)
    private Integer quota;

    @ApiModelProperty("添加时间")
    @ExcelProperty(value = "添加时间", index = 6)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @ExcelProperty(value = "修改时间", index = 7)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
