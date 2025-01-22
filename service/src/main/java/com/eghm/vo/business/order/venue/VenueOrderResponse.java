package com.eghm.vo.business.order.venue;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.convertor.excel.CentToYuanConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.OrderState;
import com.eghm.enums.PayType;
import com.eghm.enums.VenueType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("订单编号")
    @ExcelProperty(value = "订单编号", index = 0)
    private String orderNo;

    @ApiModelProperty(value = "场地名称")
    @ExcelProperty(value = "场地名称", index = 1)
    private String siteTitle;

    @ApiModelProperty(value = "场馆名称")
    @ExcelProperty(value = "场馆名称", index = 2)
    private String title;

    @ApiModelProperty(value = "场馆类型")
    @ExcelProperty(value = "场馆类型", index = 3, converter = EnumExcelConverter.class)
    private VenueType venueType;

    @ApiModelProperty(value = "订单状态")
    @ExcelProperty(value = "订单状态", index = 4, converter = EnumExcelConverter.class)
    private OrderState state;

    @ApiModelProperty("联系人昵称")
    @ExcelProperty(value = "联系人昵称", index = 5)
    private String nickName;

    @ApiModelProperty("联系人电话")
    @ExcelProperty(value = "联系人电话", index = 6)
    private String mobile;

    @ApiModelProperty(value = "总付款金额=单价*数量+总快递费-总优惠金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    @ExcelProperty(value = "付款金额", index = 7, converter = CentToYuanConverter.class)
    private Integer payAmount;

    @ApiModelProperty("总优惠金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    @ExcelProperty(value = "总优惠金额", index = 8, converter = CentToYuanConverter.class)
    private Integer discountAmount;

    @ApiModelProperty("下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "下单时间", index = 9)
    private LocalDateTime createTime;

    @ApiModelProperty("支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "支付时间", index = 10)
    private LocalDateTime payTime;

    @ApiModelProperty("支付方式")
    @ExcelProperty(value = "支付方式", index = 11, converter = EnumExcelConverter.class)
    private PayType payType;
}
