package com.eghm.vo.business.restaurant;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/12/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RestaurantResponse extends ExcelStyle {

    @ApiModelProperty(value = "商家名称")
    @ExcelProperty(value = "商家名称", index = 0)
    private String title;

    @ApiModelProperty(value = "所属商户")
    @ExcelProperty(value = "所属商户", index = 1)
    private String merchantName;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    @ExcelProperty(value = "状态", index = 2, converter = EnumExcelConverter.class)
    private State state;

    @ApiModelProperty(value = "营业时间")
    @ExcelProperty(value = "营业时间", index = 3)
    private String openTime;

    @ApiModelProperty("详细地址")
    @ExcelProperty(value = "详细地址", index = 4)
    private String detailAddress;

    @ApiModelProperty(value = "商家热线")
    @ExcelProperty(value = "商家热线", index = 5)
    private String phone;

    @ApiModelProperty("创建时间")
    @ExcelProperty(value = "创建时间", index = 6)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
