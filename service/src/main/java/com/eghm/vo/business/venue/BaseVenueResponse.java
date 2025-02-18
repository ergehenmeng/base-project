package com.eghm.vo.business.venue;

import com.alibaba.excel.annotation.ExcelProperty;
import com.eghm.convertor.excel.EnumExcelConverter;
import com.eghm.enums.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/2
 */

@Data
public class BaseVenueResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "场馆名称")
    @ExcelProperty(value = "场馆名称", index = 0)
    private String title;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    @ExcelProperty(value = "状态 0:待上架 1:已上架 2:强制下架", index = 2, converter = EnumExcelConverter.class)
    private State state;
}
