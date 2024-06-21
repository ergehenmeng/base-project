package com.eghm.vo.business.account;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.ChargeType;
import com.eghm.enums.ref.DirectionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 商户积分变动明细表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ScoreAccountLogResponse extends ExcelStyle {

    @ApiModelProperty(value = "商户名称")
    @ExcelProperty(value = "商户名称", index = 0)
    private String merchantName;

    @ApiModelProperty(value = "变动类型 (1:充值 2:支付收入 3:支付退款 4:抽奖支出 5:提现支出 6:关注赠送 7: 提现失败)")
    @ExcelProperty(value = "变动类型", index = 1, converter = EnumExcelConverter.class)
    private ChargeType chargeType;

    @ApiModelProperty(value = "变动积分")
    @ExcelProperty(value = "变动积分", index = 2)
    private Integer amount;

    @ApiModelProperty(value = "收支类型 1:收入 2:支出")
    @ExcelProperty(value = "收支类型", index = 3, converter = EnumExcelConverter.class)
    private DirectionType direction;

    @ApiModelProperty(value = "变动后的积分(可用积分)")
    @ExcelProperty(value = "变动后的积分(可用积分)", index = 4)
    private Integer surplusAmount;

    @ApiModelProperty(value = "关联单号")
    @ExcelProperty(value = "关联单号", index = 5)
    private String tradeNo;

    @ApiModelProperty("变动时间")
    @ExcelProperty(value = "变动时间", index = 6)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "备注信息")
    @ExcelProperty(value = "备注信息", index = 7)
    private String remark;
}
