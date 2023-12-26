package com.eghm.vo.business.homestay;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/12/4
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class HomestayResponse extends ExcelStyle {

    @ApiModelProperty(value = "民宿ID")
    private Long id;

    @ApiModelProperty(value = "民宿名称")
    @ExcelProperty(value = "民宿名称", index = 0)
    private String title;

    @ApiModelProperty(value = "商户名称")
    @ExcelProperty(value = "商户名称", index = 1)
    private String merchantName;

    @ApiModelProperty(value = "星级 5:五星级 4:四星级 3:三星级 0: 其他")
    @ExcelProperty(value = "星级", index = 2)
    private Integer level;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    @ExcelProperty(value = "状态", index = 3)
    private State state;

    @ApiModelProperty(value = "详细地址")
    @ExcelProperty(value = "详细地址", index = 4)
    private String detailAddress;

    @ApiModelProperty(value = "联系电话")
    @ExcelProperty(value = "联系电话", index = 5)
    private String phone;

    @ApiModelProperty("分数")
    @ExcelProperty(value = "分数", index = 6)
    private BigDecimal score;

    @ApiModelProperty("添加时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间", index = 7)
    private LocalDateTime createTime;
}
