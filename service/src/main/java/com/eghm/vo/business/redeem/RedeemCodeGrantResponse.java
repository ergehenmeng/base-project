package com.eghm.vo.business.redeem;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.annotation.Desensitization;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.FieldType;
import com.eghm.enums.RedeemState;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/2/11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RedeemCodeGrantResponse extends ExcelStyle {

    @Schema(description = "cdKey")
    @Desensitization(FieldType.MOBILE_PHONE)
    @ExcelProperty(value = "cdKey",index = 0)
    private String cdKey;

    @Schema(description = "使用状态 0:待使用 1:已使用 2:已过期")
    @ExcelProperty(index = 1, value = "使用状态", converter = EnumExcelConverter.class)
    private RedeemState state;

    @Schema(description = "发放时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "发放时间", index = 2)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "用户昵称")
    @ExcelProperty(value = "用户昵称", index = 3)
    private String nickName;

    @Schema(description = "手机号")
    @ExcelProperty(value = "手机号", index = 4)
    private String mobile;

    @Schema(description = "使用时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "使用时间", index = 5)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime useTime;


}
