package com.eghm.dto.business.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/1/5
 */
@Data
public class ActivityDeleteRequest {

    @Schema(description = "活动标题")
    private String title;

    @Schema(description = "id")
    private Long id;
}
