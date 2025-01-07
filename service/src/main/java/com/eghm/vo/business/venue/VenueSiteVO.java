package com.eghm.vo.business.venue;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/2/2
 */

@Data
public class VenueSiteVO {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "场地名称")
    private String title;

}
