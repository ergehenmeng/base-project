package com.eghm.vo.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2022/9/1
 */
@Data
public class BaseConfigResponse {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "是否设置了价格 true:是 false:否")
    private Boolean hasSet;

    @Schema(description = "日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate configDate;

}
