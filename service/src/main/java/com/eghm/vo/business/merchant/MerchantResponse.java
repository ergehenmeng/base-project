package com.eghm.vo.business.merchant;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.convertor.excel.MerchantTypeConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.EnterpriseType;
import com.eghm.enums.UserState;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 商家信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MerchantResponse extends ExcelStyle {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "商家名称")
    @ExcelProperty(value = "商家名称", index = 0)
    private String merchantName;

    @Schema(description = "商家类型 1:景区 2:民宿 4:餐饮 8:零售 16:线路 32:场馆")
    @ExcelProperty(value = "商家类型", index = 1, converter = MerchantTypeConverter.class)
    private Integer type;

    @Schema(description = "商家状态: 0:锁定 1:正常 2:销户")
    @ExcelProperty(value = "商家状态", index = 2, converter = EnumExcelConverter.class)
    private UserState state;

    @Schema(description = "联系人电话")
    @ExcelProperty(value = "联系人电话", index = 3)
    private String mobile;

    @Schema(description = "账户名")
    @ExcelProperty(value = "账户名", index = 4)
    private String account;

    @Schema(description = "微信授权手机号")
    @ExcelProperty(value = "微信授权手机号", index = 5)
    private String authMobile;

    @Schema(description = "企业类型: 1:个人 2:企业")
    @ExcelProperty(value = "企业类型", index = 6, converter = EnumExcelConverter.class)
    private EnterpriseType enterpriseType;

    @Schema(description = "法人姓名")
    @ExcelProperty(value = "法人姓名", index = 7)
    private String legalName;

    @Schema(description = "法人身份证")
    @ExcelProperty(value = "法人身份证", index = 8)
    private String legalIdCard;

    @Schema(description = "社会统一信用代码")
    @ExcelProperty(value = "社会统一信用代码", index = 9)
    private String creditCode;

    @Schema(description = "平台服务费,单位:%")
    @ExcelProperty(value = "平台服务费(%)", index = 10)
    private BigDecimal platformServiceRate;

    @Schema(description = "创建时间")
    @ExcelProperty(value = "创建时间", index = 11)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @ExcelProperty(value = "修改时间", index = 12)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
