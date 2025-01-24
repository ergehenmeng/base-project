package com.eghm.dto.ext;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 复选框下拉列表
 *
 * @author 二哥很猛
 * @since 2018/11/30 15:19
 */
public record CheckBox(@Schema(description = "值") Long value, @Schema(description = "描述") String desc) {
}
