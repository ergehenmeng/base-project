package com.eghm.vo.business.homestay.room;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.RefundType;
import com.eghm.enums.ref.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2022/12/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HomestayRoomResponse extends ExcelStyle {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("房型名称")
    @ExcelProperty(value = "房型名称", index = 0)
    private String title;

    @ApiModelProperty("民宿名称")
    @ExcelProperty(value = "民宿名称", index = 1)
    private String homestayTitle;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    @ExcelProperty(value = "状态", index = 2, converter = EnumExcelConverter.class)
    private State state;

    @ApiModelProperty(value = "房型类型 1:整租 2:单间 3:合租")
    @ExcelProperty(value = "房型类型(1:整租 2:单间 3:合租)", index = 3)
    private Integer roomType;

    @ApiModelProperty(value = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    @ExcelProperty(value = "退款方式", index = 4, converter = EnumExcelConverter.class)
    private RefundType refundType;

    @ApiModelProperty(value = "几室")
    @ExcelProperty(value = "几室", index = 5)
    private Integer room;

    @ApiModelProperty(value = "几厅")
    @ExcelProperty(value = "几厅", index = 6)
    private Integer hall;

    @ApiModelProperty(value = "几厨")
    @ExcelProperty(value = "几厨", index = 7)
    private Integer kitchen;

    @ApiModelProperty(value = "卫生间数")
    @ExcelProperty(value = "卫生间数", index = 8)
    private Integer washroom;

    @ApiModelProperty(value = "面积")
    @ExcelProperty(value = "面积", index = 9)
    private Integer dimension;

    @ApiModelProperty(value = "居住人数")
    @ExcelProperty(value = "居住人数", index = 10)
    private Integer resident;

    @ApiModelProperty(value = "床数")
    @ExcelProperty(value = "床数", index = 11)
    private Integer bed;

    @ApiModelProperty("创建时间")
    @ExcelProperty(value = "创建时间", index = 12)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
