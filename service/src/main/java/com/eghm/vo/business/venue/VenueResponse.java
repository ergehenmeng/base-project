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

    @ApiModelProperty(value = "场馆类型 (1:篮球馆 2:网球馆 3:羽毛球馆 4:乒乓球馆 5:游泳馆 6:健身馆 7:瑜伽馆 8:保龄馆 9:足球馆 10:排球馆 11:田径馆 12:综合馆 13:跆拳道馆)")
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
