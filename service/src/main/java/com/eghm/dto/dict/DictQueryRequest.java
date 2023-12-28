package com.eghm.dto.dict;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @date 2019/1/14 11:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictQueryRequest extends PagingQuery {

    @ApiModelProperty("是否锁定 true:锁定 false:未锁定")
    private Boolean locked;
}
