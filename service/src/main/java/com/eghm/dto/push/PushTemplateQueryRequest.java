package com.eghm.dto.push;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.validation.annotation.OptionInt;
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

    @ApiModelProperty("0:关闭 1:开启")
    @OptionInt(value = {0, 1}, required = false)
    private Integer state;

}
