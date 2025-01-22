package com.eghm.vo.business.order.venue;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.convertor.excel.CentToYuanConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import com.eghm.enums.ref.VenueType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/2/5
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class VenueOrderResponse extends ExcelStyle {

    @Schema(description = "订单编号")
    @ExcelProperty(value = "订单编号", index = 0)
    private String orderNo;

    @Schema(description = "场地名称")
    @ExcelProperty(value = "场地名称", index = 1)
    private String siteTitle;

    @Schema(description = "场馆名称")
    @ExcelProperty(value = "场馆名称", index = 2)
    private String title;

    @Schema(description = "场馆类型")
    @ExcelProperty(value = "场馆类型", index = 3, converter = EnumExcelConverter.class)
    private VenueType venueType;

    @Schema(description = "订单状态 0:待支付 1:支付中 2:待使用 3:待自提 4:待发货 5:部分发货 6:待收货 7:退款中 8:订单完成 9:已关闭 10:支付异常 11:退款异常")
    @ExcelProperty(value = "订单状态", index = 4, converter = EnumExcelConverter.class)
    private OrderState state;

    @Schema(description = "联系人昵称")
    @ExcelProperty(value = "联系人昵称", index = 5)
    private String nickName;

    @Schema(description = "联系人电话")
    @ExcelProperty(value = "联系人电话", index = 6)
    private String mobile;

    @Schema(description = "总付款金额=单价*数量+总快递费-总优惠金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    @ExcelProperty(value = "付款金额", index = 7, converter = CentToYuanConverter.class)
    private Integer payAmount;

    @Schema(description = "总优惠金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    @ExcelProperty(value = "总优惠金额", index = 8, converter = CentToYuanConverter.class)
    private Integer discountAmount;

    @Schema(description = "下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "下单时间", index = 9)
    private LocalDateTime createTime;

    @Schema(description = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "支付时间", index = 10)
    private LocalDateTime payTime;

    @Schema(description = "支付方式")
    @ExcelProperty(value = "支付方式", index = 11, converter = EnumExcelConverter.class)
    private PayType payType;
}
