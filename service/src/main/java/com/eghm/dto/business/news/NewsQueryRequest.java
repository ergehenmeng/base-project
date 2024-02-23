package com.eghm.dto.business.news;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2023/12/29
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class NewsQueryRequest extends PagingQuery {

    @ApiModelProperty(value = "资讯编码", required = true)
    @NotBlank(message = "资讯编码不能为空")
    private String code;
}
