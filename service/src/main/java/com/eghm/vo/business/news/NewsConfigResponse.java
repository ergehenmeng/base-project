package com.eghm.vo.business.news;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/12/29
 */
@Data
public class NewsConfigResponse {

    @ApiModelProperty(value = "分类标题")
    private String title;

    @ApiModelProperty(value = "资讯编码")
    private String code;

}
