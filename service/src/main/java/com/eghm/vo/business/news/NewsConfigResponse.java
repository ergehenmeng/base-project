package com.eghm.vo.business.news;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/12/29
 */
@Data
public class NewsConfigResponse {

    @Schema(description = "分类标题")
    private String title;

    @Schema(description = "资讯编码")
    private String code;

}
