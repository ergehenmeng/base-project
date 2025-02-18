package com.eghm.vo.business.scenic.ticket;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.convertor.excel.BooleanExcelConverter;
import com.eghm.convertor.excel.CentToYuanConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.State;
import com.eghm.enums.TicketType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TicketResponse extends ExcelStyle {

    @ApiModelProperty("门票id")
    private Long id;

    @ApiModelProperty(value = "门票所属景区")
    private Long scenicId;

    @ApiModelProperty(value = "门票名称")
    @ExcelProperty(value = "门票名称", index = 0)
    private String title;

    @ApiModelProperty("所属景区")
    @ExcelProperty(value = "所属景区", index = 1)
    private String scenicName;

    @ApiModelProperty(value = "票种类型 1:成人 2:老人 3:儿童  4:演出 5:活动 6:研学 7:组合")
    @ExcelProperty(value = "票种", index = 2, converter = EnumExcelConverter.class)
    private TicketType category;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2: 强制下架")
    @ExcelProperty(value = "状态", index = 3, converter = EnumExcelConverter.class)
    private State state;

    @ApiModelProperty(value = "销售价")
    @JsonSerialize(using = CentToYuanSerializer.class)
    @ExcelProperty(value = "销售价", index = 4, converter = CentToYuanConverter.class)
    private Integer salePrice;

    @ApiModelProperty(value = "真实销数")
    @ExcelProperty(value = "真实销数", index = 5)
    private Integer saleNum;

    @ApiModelProperty(value = "可预订时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value = "可预订时间", index = 6)
    @DateTimeFormat(value = "yyyy-MM-dd")
    private LocalDate startDate;

    @ApiModelProperty(value = "可预订时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value = "可预订时间", index = 7)
    @DateTimeFormat(value = "yyyy-MM-dd")
    private LocalDate endDate;

    @ApiModelProperty(value = "剩余库存")
    @ExcelProperty(value = "剩余库存", index = 8)
    private Integer stock;

    @ApiModelProperty(value = "是否实名 false:不实名 true:实名")
    @ExcelProperty(value = "是否实名", index = 10, converter = BooleanExcelConverter.class)
    private Boolean realBuy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间", index = 11)
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "更新时间", index = 12)
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
