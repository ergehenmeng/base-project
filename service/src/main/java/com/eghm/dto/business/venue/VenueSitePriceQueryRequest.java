package com.eghm.dto.business.venue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author 二哥很猛
 * @since 2024/1/31
 */

@Data
public class VenueSitePriceQueryRequest {

    @ApiModelProperty(value = "场次id", required = true)
    @NotNull(message = "场次id不能为空")
    private Long venueSiteId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "请选择日期")
    private LocalDate nowDate;
}
