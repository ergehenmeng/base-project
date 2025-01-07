package com.eghm.vo.business.restaurant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 餐饮标签
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-10-09
 */
@Data
public class TagSelectResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "标签名称")
    private String title;

    @Schema(description = "状态 0:禁用 1:正常")
    private Boolean state;
}
