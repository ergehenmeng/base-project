package com.eghm.vo.business.order.homestay;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.convertor.SplitterArraySerializer;
import com.eghm.convertor.excel.CentToYuanConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ConfirmState;
import com.eghm.enums.OrderState;
import com.eghm.enums.PayType;
import com.eghm.enums.RefundState;
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
public class HomestayOrderResponse extends ExcelStyle {

    @Schema(description = "封面")
    @JsonSerialize(using = SplitterArraySerializer.class)
    private String coverUrl;

    @Schema(description = "订单编号")
    @ExcelProperty(value = "订单编号", index = 0)
    private String orderNo;

    @Schema(description = "房型名称")
    @ExcelProperty(value = "房型名称", index = 1)
    private String title;

    @Schema(description = "民宿名称")
    @ExcelProperty(value = "民宿名称", index = 2)
    private String homestayName;

    @Schema(description = "订单状态 0:待支付 1:支付中 2:待使用 3:待自提 4:待发货 5:待收货 6:待成团 7:订单完成 8:已关闭")
    @ExcelProperty(value = "订单状态", index = 3, converter = EnumExcelConverter.class)
    private OrderState state;

    @Schema(description = "退款状态 1:退款申请中 2:退款中 3:退款拒绝 4:退款成功 5:退款失败(该状态和退款中在C端用户看来都是退款中) 6:线下退款(该状态与退款成功在C端用户看来是一样的)")
    @ExcelProperty(value = "退款状态", index = 4, converter = EnumExcelConverter.class)
    private RefundState refundState;

    @Schema(description = "确认状态 0:待确认 1:确认有房 2:确认无房 3:自动确认有房")
    @ExcelProperty(value = "确认状态", index = 5, converter = EnumExcelConverter.class)
    private ConfirmState confirmState;

    @Schema(description = "购买数量")
    @ExcelProperty(value = "购买数量", index = 6)
    private Integer num;

    @Schema(description = "联系人姓名")
    @ExcelProperty(value = "联系人姓名", index = 7)
    private String nickName;

    @Schema(description = "联系人手机号")
    @ExcelProperty(value = "联系人手机号", index = 8)
    private String mobile;

    @Schema(description = "付款金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    @ExcelProperty(value = "付款金额", index = 9, converter = CentToYuanConverter.class)
    private Integer payAmount;

    @Schema(description = "总优惠金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    @ExcelProperty(value = "总优惠金额", index = 10, converter = CentToYuanConverter.class)
    private Integer discountAmount;

    @Schema(description = "下单时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "下单时间", index = 11)
    private LocalDateTime createTime;

    @Schema(description = "支付时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "支付时间", index = 12)
    private LocalDateTime payTime;

    @Schema(description = "支付方式(支付成功才会有支付方式)")
    @ExcelProperty(value = "支付方式", index = 13, converter = EnumExcelConverter.class)
    private PayType payType;

}
