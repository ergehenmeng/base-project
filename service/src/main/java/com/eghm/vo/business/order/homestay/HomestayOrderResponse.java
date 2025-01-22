package com.eghm.vo.business.order.homestay;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.CentToYuanSerializer;
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
public class HomestayOrderResponse extends ExcelStyle {

    @Schema(description = "封面")
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

    @Schema(description = "订单状态 0:待支付 1:支付中 2:待使用 3:待自提 4:待发货 5:部分发货 6:待收货 7:退款中 8:订单完成 9:已关闭 10:支付异常 11:退款异常")
    @ExcelProperty(value = "订单状态", index = 3, converter = EnumExcelConverter.class)
    private OrderState state;

    @Schema(description = "购买数量")
    @ExcelProperty(value = "购买数量", index = 4)
    private Integer num;

    @Schema(description = "联系人姓名")
    @ExcelProperty(value = "联系人姓名", index = 5)
    private String nickName;

    @Schema(description = "联系人手机号")
    @ExcelProperty(value = "联系人手机号", index = 6)
    private String mobile;

    @Schema(description = "付款金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    @ExcelProperty(value = "付款金额", index = 7, converter = CentToYuanConverter.class)
    private Integer payAmount;

    @Schema(description = "总优惠金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    @ExcelProperty(value = "总优惠金额", index = 8, converter = CentToYuanConverter.class)
    private Integer discountAmount;

    @Schema(description = "下单时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "下单时间", index = 9)
    private LocalDateTime createTime;

    @Schema(description = "支付时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "支付时间", index = 10)
    private LocalDateTime payTime;

    @Schema(description = "支付方式(支付成功才会有支付方式)")
    @ExcelProperty(value = "支付方式", index = 11, converter = EnumExcelConverter.class)
    private PayType payType;

}
