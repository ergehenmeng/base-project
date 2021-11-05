package com.eghm.model.dto.push;

import com.eghm.model.dto.ext.PagingQuery;
import com.eghm.model.validation.annotation.OptionByte;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @date 2019/8/29 16:20
 */
@Getter
@Setter
public class PushTemplateQueryRequest extends PagingQuery {

    private static final long serialVersionUID = 2652554259813794315L;

    @ApiModelProperty("0:关闭 1:开启")
    @OptionByte(value = {0, 1}, required = false)
    private Byte state;

}
