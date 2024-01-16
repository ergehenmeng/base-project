package com.eghm.vo.business.merchant;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.convertor.excel.MerchantTypeConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.UserState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "商家名称")
    @ExcelProperty(value = "商家名称", index = 0)
    private String merchantName;

    @ApiModelProperty(value = "商家类型: 1:景区 2: 民宿 4: 餐饮 8: 特产 16: 线路")
    @ExcelProperty(value = "商家类型", index = 1, converter = MerchantTypeConverter.class)
    private Integer type;

    @ApiModelProperty(value = "商家状态: 0:锁定 1:正常 2:销户")
    @ExcelProperty(value = "商家状态", index = 2, converter = EnumExcelConverter.class)
    private UserState state;

    @ApiModelProperty(value = "联系人姓名")
    @ExcelProperty(value = "联系人姓名", index = 3)
    private String nickName;

    @ApiModelProperty(value = "联系人电话")
    @ExcelProperty(value = "联系人电话", index = 4)
    private String mobile;

    @ApiModelProperty(value = "微信授权手机号")
    @ExcelProperty(value = "微信授权手机号", index = 5)
    private String authMobile;

    @ApiModelProperty(value = "微信openId")
    @ExcelProperty(value = "微信openId", index = 6)
    private String openId;

    @ApiModelProperty(value = "创建时间")
    @ExcelProperty(value = "创建时间", index = 7)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
