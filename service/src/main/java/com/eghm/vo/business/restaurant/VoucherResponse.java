package com.eghm.vo.business.restaurant;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.BooleanExcelConverter;
import com.eghm.convertor.excel.CentToYuanConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
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

    @ApiModelProperty(value = "商品名称")
    @ExcelProperty(value = "商品名称", index = 0)
    private String title;

    @ApiModelProperty(value = "所属店铺")
    @ExcelProperty(value = "所属店铺", index = 1)
    private String restaurantName;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    @ExcelProperty(value = "状态", index = 2, converter = EnumExcelConverter.class)
    private State state;

    @ApiModelProperty("是否热销商品 true:是 false:不是")
    @ExcelProperty(value = "是否热销商品", index = 3, converter = BooleanExcelConverter.class)
    private Boolean hotSell;

    @ApiModelProperty(value = "销售价")
    @ExcelProperty(value = "销售价", index = 4, converter = CentToYuanConverter.class)
    private Integer salePrice;

    @ApiModelProperty(value = "销量")
    @ExcelProperty(value = "销量", index = 5)
    private Integer saleNum;

    @ApiModelProperty("评分")
    @ExcelProperty(value = "评分", index = 6)
    private BigDecimal score;

    @ApiModelProperty(value = "购买说明")
    @ExcelProperty(value = "购买说明", index = 7)
    private String depict;

    @ApiModelProperty(value = "限购数量")
    @ExcelProperty(value = "限购数量", index = 8)
    private Integer quota;

    @ApiModelProperty("添加时间")
    @ExcelProperty(value = "添加时间", index = 10)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
