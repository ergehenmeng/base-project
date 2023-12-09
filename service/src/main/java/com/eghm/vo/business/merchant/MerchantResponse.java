package com.eghm.vo.business.merchant;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.eghm.convertor.excel.MerchantTypeConverter;
import com.eghm.dto.ext.ExcelStyle;
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

    @ApiModelProperty(value = "商家名称")
    @ExcelProperty(value = "商家名称", index = 0)
    private String merchantName;

    @ApiModelProperty(value = "商家类型: 1:景区 2: 民宿 4: 餐饮 8: 特产 16: 线路")
    @ExcelProperty(value = "商家类型", index = 1, converter = MerchantTypeConverter.class)
    private Integer type;

    @ApiModelProperty(value = "联系人姓名")
    @ExcelProperty(value = "联系人姓名", index = 2)
    private String nickName;

    @ApiModelProperty(value = "联系人电话")
    @ExcelProperty(value = "联系人电话", index = 3)
    private String mobile;

    @ApiModelProperty(value = "创建时间")
    @ExcelProperty(value = "创建时间", index = 4)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
