package com.eghm.vo.business.restaurant;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "标签名称")
    private String title;

    @ApiModelProperty(value = "状态 0:禁用 1:正常")
    private Boolean state;
}
