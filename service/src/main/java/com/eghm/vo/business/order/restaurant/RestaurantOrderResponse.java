package com.eghm.vo.business.order.restaurant;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.CentToYuanConverter;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.convertor.EnumExcelConverter;
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
 * 门票订单列表vo
 * @author 二哥很猛
 * @since 2023/7/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RestaurantOrderResponse extends ExcelStyle {

    @ApiModelProperty("图片")
    private String coverUrl;

    @ApiModelProperty("订单编号")
    @ExcelProperty(value = "订单编号", index = 0)
    private String orderNo;

    @ApiModelProperty("餐饮券名称")
    @ExcelProperty(value = "餐饮券名称", index = 1)
    private String title;

    @ApiModelProperty("餐饮店名称")
    @ExcelProperty(value = "餐饮店名称", index = 2)
    private String restaurantName;

    @ApiModelProperty("支付方式(支付成功才会有支付方式)")
    @ExcelProperty(value = "支付方式", index = 3, converter = EnumExcelConverter.class)
    private PayType payType;

    @ApiModelProperty("购买数量")
    @ExcelProperty(value = "购买数量", index = 4)
    private Integer num;

    @ApiModelProperty(value = "订单状态")
    @ExcelProperty(value = "订单状态", index = 5, converter = EnumExcelConverter.class)
    private OrderState state;

    @ApiModelProperty("优惠金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    @ExcelProperty(value = "优惠金额", index = 6, converter = CentToYuanConverter.class)
    private Integer discountAmount;

    @ApiModelProperty("付款金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    @ExcelProperty(value = "付款金额", index = 7, converter = CentToYuanConverter.class)
    private Integer payAmount;

    @ApiModelProperty("购买人昵称")
    @ExcelProperty(value = "购买人昵称", index = 8)
    private String nickName;

    @ApiModelProperty("购买人手机号")
    @ExcelProperty(value = "购买人手机号", index = 9)
    private String mobile;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "订单创建时间", index = 10)
    private LocalDateTime createTime;

    @ApiModelProperty("支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "支付时间", index = 11)
    private LocalDateTime payTime;

    @ApiModelProperty("完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "完成时间", index = 12)
    private LocalDateTime completeTime;

    @ApiModelProperty("关闭类型 1:过期自动关闭 2:用户取消 3: 退款完成")
    @ExcelProperty(value = "关闭类型", index = 13, converter = EnumExcelConverter.class)
    private CloseType closeType;

    @ApiModelProperty("订单关闭时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "关闭时间", index = 14)
    private LocalDateTime closeTime;
}
