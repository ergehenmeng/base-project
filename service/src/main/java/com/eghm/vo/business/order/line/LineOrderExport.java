package com.eghm.vo.business.order.line;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.CentToYuanConverter;
import com.eghm.convertor.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 线路订单列表vo
 * @author 二哥很猛
 * @since 2023/7/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LineOrderExport extends ExcelStyle {

    @ApiModelProperty("订单编号")
    @ExcelProperty(value = "订单编号", index = 0)
    private String orderNo;

    @ApiModelProperty("线路名称")
    @ExcelProperty(value = "线路名称", index = 1)
    private String title;

    @ApiModelProperty("旅行社名称")
    @ExcelProperty(value = "旅行社名称", index = 2)
    private String travelName;

    @ApiModelProperty("支付方式(支付成功才会有支付方式)")
    @ExcelProperty(value = "支付方式", index = 3, converter = EnumExcelConverter.class)
    private PayType payType;

    @ApiModelProperty("购买数量")
    @ExcelProperty(value = "购买数量", index = 4)
    private Integer num;

    @ApiModelProperty("昵称")
    @ExcelProperty(value = "购买人姓名", index = 5)
    private String nickName;

    @ApiModelProperty("手机号")
    @ExcelProperty(value = "购买人手机号", index = 6)
    private String mobile;

    @ApiModelProperty(value = "订单状态")
    @ExcelProperty(value = "订单状态", index = 7, converter = EnumExcelConverter.class)
    private OrderState state;

    @ApiModelProperty("创建时间")
    @ExcelProperty(value = "创建时间", index = 8)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("支付时间")
    @ExcelProperty(value = "支付时间", index = 9)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @ApiModelProperty("总优惠金额")
    @ExcelProperty(value = "总优惠金额", index = 10, converter = CentToYuanConverter.class)
    private Integer discountAmount;

    @ApiModelProperty("总付款金额")
    @ExcelProperty(value = "总付款金额", index = 11, converter = CentToYuanConverter.class)
    private Integer payAmount;

}
