package com.eghm.dto.ext;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 复选框下拉列表
 *
 * @author 二哥很猛
 * @since 2018/11/30 15:19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckBox {

    @Schema(description = "实际值")
    private Long value;

    @Schema(description = "显示值")
    private String desc;

}
