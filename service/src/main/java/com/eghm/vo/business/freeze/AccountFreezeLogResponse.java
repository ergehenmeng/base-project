package com.eghm.vo.business.freeze;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.convertor.excel.CentToYuanConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ChangeType;
import com.eghm.enums.FreezeState;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/2/28
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class AccountFreezeLogResponse extends ExcelStyle {

    @Schema(description = "商户名称")
    @ExcelProperty(value = "商户名称", index = 0)
    private String merchantName;

    @Schema(description = "订单编号")
    @ExcelProperty(value = "订单编号", index = 1)
    private String orderNo;

    @Schema(description = "冻结金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    @ExcelProperty(value = "冻结金额", index = 2, converter = CentToYuanConverter.class)
    private Integer amount;

    @Schema(description = "状态 1:冻结中 2:已解冻")
    @ExcelProperty(value = "状态", index = 3, converter = EnumExcelConverter.class)
    private FreezeState state;

    @Schema(description = "变更类型 1:支付冻结, 2:退款解冻 3:订单完成解冻")
    @ExcelProperty(value = "变更类型", index = 4, converter = EnumExcelConverter.class)
    private ChangeType changeType;

    @Schema(description = "冻结时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "冻结时间", index = 5)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "解冻时间")
    @ExcelProperty(value = "解冻时间", index = 6)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime unfreezeTime;

    @Schema(description = "备注信息")
    @ExcelProperty(value = "备注信息", index = 7)
    private String remark;
}
