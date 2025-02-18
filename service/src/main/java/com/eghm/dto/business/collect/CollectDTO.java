package com.eghm.dto.business.collect;

import com.eghm.enums.CollectType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/11
 */

@Data
public class CollectDTO {

    @Schema(description = "收藏id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "收藏id不能为空")
    private Long collectId;

    @Schema(description = "收藏对象类型(1:景区 2:民宿 3:零售门店 4:零售商品 5:线路商品 6:餐饮门店 7:资讯 8:旅行社)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "收藏对象类型不能为空")
    private CollectType collectType;
}
