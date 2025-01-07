package com.eghm.vo.business.account;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.ChargeType;
import com.eghm.enums.ref.DirectionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "商户名称")
    @ExcelProperty(value = "商户名称", index = 0)
    private String merchantName;

    @Schema(description = "变动类型 (1:充值 2:支付收入 3:支付退款 4:抽奖支出 5:提现支出 6:关注赠送 7: 提现失败)")
    @ExcelProperty(value = "变动类型", index = 1, converter = EnumExcelConverter.class)
    private ChargeType chargeType;

    @Schema(description = "变动积分")
    @ExcelProperty(value = "变动积分", index = 2)
    private Integer amount;

    @Schema(description = "收支类型 1:收入 2:支出")
    @ExcelProperty(value = "收支类型", index = 3, converter = EnumExcelConverter.class)
    private DirectionType direction;

    @Schema(description = "变动后的积分(可用积分)")
    @ExcelProperty(value = "变动后的积分(可用积分)", index = 4)
    private Integer surplusAmount;

    @Schema(description = "关联单号")
    @ExcelProperty(value = "关联单号", index = 5)
    private String tradeNo;

    @Schema(description = "变动时间")
    @ExcelProperty(value = "变动时间", index = 6)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "备注信息")
    @ExcelProperty(value = "备注信息", index = 7)
    private String remark;
}
