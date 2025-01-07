package com.eghm.vo.business.homestay.room;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.BooleanExcelConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.RefundType;
import com.eghm.enums.ref.RoomType;
import com.eghm.enums.ref.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "id")
    private Long id;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "房型名称")
    @ExcelProperty(value = "房型名称", index = 0)
    private String title;

    @Schema(description = "民宿名称")
    @ExcelProperty(value = "民宿名称", index = 1)
    private String homestayTitle;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    @ExcelProperty(value = "状态", index = 2, converter = EnumExcelConverter.class)
    private State state;

    @Schema(description = "房型 1:标间 2:大床房 3:双人房 4:钟点房 5:套房 6:合租")
    @ExcelProperty(value = "房型", index = 3, converter = EnumExcelConverter.class)
    private RoomType roomType;

    @Schema(description = "平台推荐")
    @ExcelProperty(value = "平台推荐", index = 4, converter = BooleanExcelConverter.class)
    private Boolean recommend;

    @Schema(description = "退款方式 0:不支持 1:直接退款 2:审核后退款")
    @ExcelProperty(value = "退款方式", index = 5, converter = EnumExcelConverter.class)
    private RefundType refundType;

    @Schema(description = "面积")
    @ExcelProperty(value = "面积(m²)", index = 6)
    private Integer dimension;

    @Schema(description = "居住人数")
    @ExcelProperty(value = "居住人数", index = 7)
    private Integer resident;

    @Schema(description = "创建时间")
    @ExcelProperty(value = "创建时间", index = 8)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @ExcelProperty(value = "更新时间", index = 9)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
