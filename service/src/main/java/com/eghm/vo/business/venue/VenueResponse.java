package com.eghm.vo.business.venue;

import com.alibaba.excel.annotation.ExcelProperty;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.dto.ext.ExcelStyle;
import com.eghm.enums.ref.VenueType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2024/2/2
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class VenueResponse extends ExcelStyle {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "场馆封面图")
    private String coverUrl;

    @ApiModelProperty(value = "场馆名称")
    @ExcelProperty(value = "场馆名称", index = 0)
    private String title;

    @ApiModelProperty(value = "场馆类型")
    @ExcelProperty(value = "场馆类型", index = 1, converter = EnumExcelConverter.class)
    private VenueType venueType;

    @ApiModelProperty(value = "营业时间")
    @ExcelProperty(value = "营业时间", index = 2)
    private String openTime;

    @ApiModelProperty(value = "商家电话")
    @ExcelProperty(value = "商家电话", index = 3)
    private String telephone;

    @ApiModelProperty(value = "详细地址")
    @ExcelProperty(value = "详细地址", index = 4)
    private String detailAddress;

}
