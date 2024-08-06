package com.eghm.vo.business.account;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.CentToYuanConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.AccountType;
import com.eghm.enums.ref.DirectionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 商户资金变动明细表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountLogResponse extends ExcelStyle {

    @ApiModelProperty(value = "商户名称")
    @ExcelProperty(value = "商户名称", index = 0)
    private String merchantName;

    @ApiModelProperty(value = "资金变动类型 (1:订单收入 2:订单退款 3:积分提现收入 4:提现支出 5:积分充值支出)")
    @ExcelProperty(value = "资金变动类型", index = 1, converter = EnumExcelConverter.class)
    private AccountType accountType;

    @ApiModelProperty(value = "变动金额")
    @ExcelProperty(value = "变动金额", index = 2, converter = CentToYuanConverter.class)
    private Integer amount;

    @ApiModelProperty(value = "收支类型 1:收入 2:支出")
    @ExcelProperty(value = "收支类型", index = 3, converter = EnumExcelConverter.class)
    private DirectionType direction;

    @ApiModelProperty(value = "变动余额")
    @ExcelProperty(value = "变动余额", index = 4, converter = CentToYuanConverter.class)
    private Integer surplusAmount;

    @ApiModelProperty(value = "交易单号")
    @ExcelProperty(value = "交易单号", index = 5)
    private String tradeNo;

    @ApiModelProperty("操作时间")
    @ExcelProperty(value = "操作时间", index = 6)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "备注信息")
    @ExcelProperty(value = "备注信息", index = 7)
    private String remark;
}
