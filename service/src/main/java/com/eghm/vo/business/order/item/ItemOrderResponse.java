package com.eghm.vo.business.order.item;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.CentToYuanConverter;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
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
public class ItemOrderResponse extends ExcelStyle {

    @ApiModelProperty("图片")
    private String coverUrl;

    @ApiModelProperty("订单编号")
    @ExcelProperty(value = "订单编号", index = 0)
    private String orderNo;

    @ApiModelProperty("店铺名称")
    @ExcelProperty(value = "店铺名称", index = 1)
    private String storeName;

    @ApiModelProperty("支付方式(支付成功才会有支付方式)")
    @ExcelProperty(value = "支付方式", index = 2, converter = EnumExcelConverter.class)
    private PayType payType;

    @ApiModelProperty("购买数量")
    @ExcelProperty(value = "购买数量", index = 3)
    private Integer num;

    @ApiModelProperty(value = "订单状态")
    @ExcelProperty(value = "订单状态", index = 4, converter = EnumExcelConverter.class)
    private OrderState state;

    @ApiModelProperty("收货人姓名")
    @ExcelProperty(value = "收货人姓名", index = 5)
    private String nickName;

    @ApiModelProperty("收货人电话")
    @ExcelProperty(value = "收货人电话", index = 6)
    private String mobile;

    @ApiModelProperty("付款金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    @ExcelProperty(value = "付款金额", index = 7, converter = CentToYuanConverter.class)
    private Integer payAmount;

    @ApiModelProperty("优惠金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    @ExcelProperty(value = "优惠金额", index = 8, converter = CentToYuanConverter.class)
    private Integer discountAmount;

    @ApiModelProperty("支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "支付时间", index = 9)
    private LocalDateTime payTime;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "订单创建时间", index = 10)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
