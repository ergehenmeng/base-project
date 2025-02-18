package com.eghm.vo.business.withdraw;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.convertor.excel.CentToYuanConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.WithdrawState;
import com.eghm.enums.WithdrawWay;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/2/27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WithdrawLogResponse extends ExcelStyle {

    @ApiModelProperty("商户名称")
    @ExcelProperty(value = "商户名称", index = 0)
    private String merchantName;

    @ApiModelProperty(value = "0:提现中 1:提现成功 2:提现失败")
    @ExcelProperty(value = "提现状态", index = 1, converter = EnumExcelConverter.class)
    private WithdrawState state;

    @ApiModelProperty(value = "提现方式 1:手动提现 2:自动提现")
    @ExcelProperty(value = "提现方式", index = 2, converter = EnumExcelConverter.class)
    private WithdrawWay withdrawWay;

    @ApiModelProperty(value = "提现金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    @ExcelProperty(value = "提现金额", index = 3, converter = CentToYuanConverter.class)
    private Integer amount;

    @ApiModelProperty(value = "提现手续费")
    @JsonSerialize(using = CentToYuanSerializer.class)
    @ExcelProperty(value = "提现手续费", index = 4, converter = CentToYuanConverter.class)
    private Integer fee;

    @ApiModelProperty(value = "申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "申请时间", index = 5)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "到账时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "到账时间", index = 6)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentTime;

    @ApiModelProperty(value = "提现流水号")
    @ExcelProperty(value = "提现流水号", index = 7)
    private String refundNo;

    @ApiModelProperty(value = "第三方流水号")
    @ExcelProperty(value = "第三方流水号", index = 8)
    private String outRefundNo;

    @ApiModelProperty(value = "用户姓名")
    @ExcelProperty(value = "用户姓名", index = 9)
    private String realName;

    @ApiModelProperty(value = "银行卡类型")
    @ExcelProperty(value = "银行卡类型", index = 10)
    private String bankType;

    @ApiModelProperty(value = "银行卡号")
    @ExcelProperty(value = "银行卡号", index = 11)
    private String bankNum;

    @ApiModelProperty(value = "备注信息")
    @ExcelProperty(value = "备注信息", index = 12)
    private String remark;

}
