package com.eghm.vo.business.item;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.convertor.excel.CentToYuanConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.DeliveryType;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "商品id")
    private Long id;

    @ApiModelProperty(value = "所属店铺")
    private Long storeId;

    @ApiModelProperty("店铺名称")
    @ExcelProperty(value = "店铺名称", index = 0)
    private String storeName;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2: 强制下架")
    @ExcelProperty(value = "状态", index = 1, converter = EnumExcelConverter.class)
    private State state;

    @ApiModelProperty(value = "商品名称")
    @ExcelProperty(value = "商品名称", index = 2)
    private String title;

    @ApiModelProperty(value = "限购数量")
    @ExcelProperty(value = "限购数量", index = 3)
    private Integer quota;

    @ApiModelProperty(value = "交付方式 1:门店自提 2:快递包邮")
    @ExcelProperty(value = "交付方式", index = 4, converter = EnumExcelConverter.class)
    private DeliveryType deliveryType;

    @ApiModelProperty(value = "最低价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    @ExcelProperty(value = "最低价格", index = 6, converter = CentToYuanConverter.class)
    private Integer minPrice;

    @ApiModelProperty(value = "最高价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    @ExcelProperty(value = "最高价格", index = 7, converter = CentToYuanConverter.class)
    private Integer maxPrice;

    @ApiModelProperty(value = "销售数量(所有规格销售总量)")
    @ExcelProperty(value = "销售数量", index = 8)
    private Integer saleNum;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "添加时间", index = 9)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "更新时间", index = 10)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
