package com.eghm.dto.business.collect;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2024/1/11
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CollectQueryDTO extends PagingQuery {

    @ApiModelProperty(value = "当前登录用户", hidden = true)
    @Assign
    private Long memberId;

    @ApiModelProperty("1:商品 2:店铺")
    private Integer type;
}
