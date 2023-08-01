package com.eghm.vo.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @date 2022/9/1
 */
@Data
public class BaseConfigResponse {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("是否设置了价格 true:是 false:否")
    private Boolean hasSet;

    @ApiModelProperty("日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate configDate;

}
