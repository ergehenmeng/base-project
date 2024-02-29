package com.eghm.vo.business.item.store;

import com.alibaba.excel.annotation.ExcelProperty;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 店铺信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ItemStoreResponse extends ExcelStyle {

    @ApiModelProperty("店铺id")
    private Long id;

    @ApiModelProperty(value = "店铺logo")
    private String logoUrl;

    @ApiModelProperty("店铺名称")
    @ExcelProperty(value = "店铺名称", index = 0)
    private String title;

    @ApiModelProperty(value = "所属商户")
    @ExcelProperty(value = "所属商户", index = 1)
    private String merchantName;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    @ExcelProperty(value = "状态 0:待上架 1:已上架 2:强制下架", index = 2, converter = EnumExcelConverter.class)
    private State state;

    @ApiModelProperty(value = "商家电话")
    @ExcelProperty(value = "商家电话", index = 3)
    private String telephone;

    @ApiModelProperty("评分")
    @ExcelProperty(value = "评分", index = 4)
    private BigDecimal score;

    @ApiModelProperty(value = "营业时间")
    @ExcelProperty(value = "营业时间", index = 5)
    private String openTime;

    @ApiModelProperty(value = "详细地址")
    @ExcelProperty(value = "详细地址", index = 6)
    private String detailAddress;

}
