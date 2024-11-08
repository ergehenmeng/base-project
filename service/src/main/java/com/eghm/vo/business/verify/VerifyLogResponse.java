package com.eghm.vo.business.verify;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.dto.ext.ExcelStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author wyb
 * @since 2023/6/13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class VerifyLogResponse extends ExcelStyle {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "商户名称")
    @ExcelProperty(value = "商户名称", index = 0)
    private String merchantName;

    @ApiModelProperty(value = "订单编号")
    @ExcelProperty(value = "订单编号", index = 1)
    private String orderNo;

    @ApiModelProperty(value = "核销人")
    @ExcelProperty(value = "核销人", index = 2)
    private String verifyName;

    @ApiModelProperty(value = "核销数量")
    @ExcelProperty(value = "核销数量", index = 3)
    private Integer num;

    @ApiModelProperty(value = "核销备注")
    @ExcelProperty(value = "核销备注", index = 4)
    private String remark;

    @ApiModelProperty(value = "核销时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "核销时间", index = 5)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
