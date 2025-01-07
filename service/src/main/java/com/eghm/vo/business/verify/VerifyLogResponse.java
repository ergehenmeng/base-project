package com.eghm.vo.business.verify;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.dto.ext.ExcelStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "商户名称")
    @ExcelProperty(value = "商户名称", index = 0)
    private String merchantName;

    @Schema(description = "订单编号")
    @ExcelProperty(value = "订单编号", index = 1)
    private String orderNo;

    @Schema(description = "核销人")
    @ExcelProperty(value = "核销人", index = 2)
    private String verifyName;

    @Schema(description = "核销数量")
    @ExcelProperty(value = "核销数量", index = 3)
    private Integer num;

    @Schema(description = "核销备注")
    @ExcelProperty(value = "核销备注", index = 4)
    private String remark;

    @Schema(description = "核销时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "核销时间", index = 5)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
