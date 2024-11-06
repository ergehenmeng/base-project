package com.eghm.dto.operate.push;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @since 2019/8/29 16:20
 */
@Getter
@Setter
public class PushTemplateQueryRequest extends PagingQuery {

    @ApiModelProperty("状态 false:关闭 true:开启")
    private Boolean state;

}
