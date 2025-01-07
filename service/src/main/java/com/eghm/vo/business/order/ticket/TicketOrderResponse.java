package com.eghm.vo.business.order.ticket;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.convertor.excel.CentToYuanConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "订单编号")
    @ExcelProperty(value = "订单编号", index = 0)
    private String orderNo;

    @Schema(description = "门票名称")
    @ExcelProperty(value = "门票名称", index = 1)
    private String title;

    @Schema(description = "景区名称")
    @ExcelProperty(value = "景区名称", index = 2)
    private String scenicName;

    @Schema(description = "订单状态")
    @ExcelProperty(value = "订单状态", index = 3, converter = EnumExcelConverter.class)
    private OrderState state;

    @Schema(description = "购买数量")
    @ExcelProperty(value = "购买数量", index = 4)
    private Integer num;

    @Schema(description = "订单联系人")
    @ExcelProperty(value = "订单联系人", index = 5)
    private String mobile;

    @Schema(description = "付款金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    @ExcelProperty(value = "付款金额", index = 6, converter = CentToYuanConverter.class)
    private Integer payAmount;

    @Schema(description = "优惠金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    @ExcelProperty(value = "优惠金额", index = 7, converter = CentToYuanConverter.class)
    private Integer discountAmount;

    @Schema(description = "下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "下单时间", index = 8)
    private LocalDateTime createTime;

    @Schema(description = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "支付时间", index = 9)
    private LocalDateTime payTime;

    @Schema(description = "支付方式")
    @ExcelProperty(value = "支付方式", index = 10, converter = EnumExcelConverter.class)
    private PayType payType;

}
