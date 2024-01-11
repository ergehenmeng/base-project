package com.eghm.vo.business.collect;

import com.eghm.enums.ref.CollectType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/11
 */

@Data
@AllArgsConstructor
public class MemberCollectVO {

    @ApiModelProperty(value = "收藏id")
    private Long collectId;

    @ApiModelProperty(value = "收藏对象类型")
    private CollectType collectType;
}
