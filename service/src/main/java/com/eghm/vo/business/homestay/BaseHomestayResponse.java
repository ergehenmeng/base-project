package com.eghm.vo.business.homestay;

import com.alibaba.excel.annotation.ExcelProperty;
import com.eghm.enums.State;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/12/4
 */

@Data
public class BaseHomestayResponse {

    @ApiModelProperty(value = "民宿ID")
    private Long id;

    @ApiModelProperty(value = "民宿名称")
    @ExcelProperty(value = "民宿名称", index = 0)
    private String title;

    @ApiModelProperty(value = "状态 0:待上架 1:已上架 2:强制下架")
    @ExcelProperty(value = "状态", index = 3)
    private State state;

}
