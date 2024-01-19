package com.eghm.vo.business.order.ticket;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.convertor.excel.CentToYuanConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.CloseType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author wyb
 * @since 2023/6/8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TicketOrderResponse extends ExcelStyle {

    @ApiModelProperty("订单编号")
    @ExcelProperty(value = "订单编号", index = 0)
    private String orderNo;

    @ApiModelProperty("门票名称")
    @ExcelProperty(value = "门票名称", index = 1)
    private String title;

    @ApiModelProperty("景区名称")
    @ExcelProperty(value = "景区名称", index = 2)
    private String scenicName;

    @ApiModelProperty("支付方式")
    @ExcelProperty(value = "支付方式", index = 3, converter = EnumExcelConverter.class)
    private PayType payType;

    @ApiModelProperty("购买数量")
    @ExcelProperty(value = "购买数量", index = 4)
    private Integer num;

    @ApiModelProperty("订单联系人")
    @ExcelProperty(value = "订单联系人", index = 5)
    private String mobile;

    @ApiModelProperty(value = "订单状态")
    @ExcelProperty(value = "订单状态", index = 6, converter = EnumExcelConverter.class)
    private OrderState state;

    @ApiModelProperty("总优惠金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    @ExcelProperty(value = "总优惠金额", index = 7, converter = CentToYuanConverter.class)
    private Integer discountAmount;

    @ApiModelProperty("订单关闭时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "订单关闭时间", index = 8)
    @sinceTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closeTime;

    @ApiModelProperty(value = "订单关闭方式")
    @ExcelProperty(value = "订单关闭方式", index = 9, converter = EnumExcelConverter.class)
    private CloseType closeType;

    @ApiModelProperty("付款金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    @ExcelProperty(value = "付款金额", index = 10, converter = CentToYuanConverter.class)
    private Integer payAmount;

    @ApiModelProperty("支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @sinceTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "支付时间", index = 11)
    private LocalDateTime payTime;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @sinceTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "订单创建时间", index = 12)
    private LocalDateTime createTime;
}
