package com.eghm.dto.business.collect;

import com.eghm.enums.ref.CollectType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/1/11
 */

@Data
public class CollectDTO {

    @ApiModelProperty(value = "收藏id", required = true)
    @NotNull(message = "收藏id不能为空")
    private Long collectId;

    @ApiModelProperty(value = "收藏对象类型(1:景区 2:民宿 3:零售门店 4:零售商品 5:线路商品 6:餐饮门店 7:资讯 8:旅行社)", required = true)
    @NotNull(message = "收藏对象类型不能为空")
    private CollectType collectType;
}
