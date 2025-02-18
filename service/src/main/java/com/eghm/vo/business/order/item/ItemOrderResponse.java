package com.eghm.vo.business.order.item;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.CentToYuanSerializer;
import com.eghm.convertor.excel.CentToYuanConverter;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.OrderState;
import com.eghm.enums.PayType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 门票订单列表vo
 *
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

    @ApiModelProperty("商品名称")
    @ExcelProperty(value = "商品名称", index = 1)
    private String title;

    @ApiModelProperty("店铺名称")
    @ExcelProperty(value = "店铺名称", index = 2)
    private String storeName;

    @ApiModelProperty("购买数量")
    @ExcelProperty(value = "购买数量", index = 3)
    private Integer num;

    @ApiModelProperty(value = "订单状态 0:待支付 1:支付中 2:待使用 3:待自提 4:待发货 5:部分发货 6:待收货 7:退款中 8:订单完成 9:已关闭 10:支付异常 11:退款异常")
    @ExcelProperty(value = "订单状态", index = 4, converter = EnumExcelConverter.class)
    private OrderState state;

    @ApiModelProperty("收货人姓名")
    @ExcelProperty(value = "收货人姓名", index = 5)
    private String nickName;

    @ApiModelProperty("收货人电话")
    @ExcelProperty(value = "收货人电话", index = 6)
    private String mobile;

    @ApiModelProperty("优惠金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    @ExcelProperty(value = "优惠金额", index = 7, converter = CentToYuanConverter.class)
    private Integer discountAmount;

    @ApiModelProperty("付款金额")
    @JsonSerialize(using = CentToYuanSerializer.class)
    @ExcelProperty(value = "付款金额", index = 8, converter = CentToYuanConverter.class)
    private Integer payAmount;

    @ApiModelProperty("下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "下单时间", index = 9)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "支付时间", index = 10)
    private LocalDateTime payTime;

    @ApiModelProperty(value = "支付方式")
    @ExcelProperty(value = "支付方式", index = 11, converter = EnumExcelConverter.class)
    private PayType payType;

}
