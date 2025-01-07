package com.eghm.vo.poi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/12/22
 */

@Data
public class BasePoiAreaResponse {

    @Schema(description = "区域名称")
    private String title;

    @Schema(description = "区域编号")
    private String code;
}
