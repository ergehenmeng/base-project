package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.SpecLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品规格配置表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-06
 */
@Data
@TableName("item_spec")
@EqualsAndHashCode(callSuper = true)
public class ItemSpec extends BaseEntity {

    @Schema(description = "商品id")
    private Long itemId;

    /**
     * 颜色
     */
    @Schema(description = "规格名(多规格时: 名一样,但值不一样)")
    private String specName;

    /**
     * 红色 黑色
     */
    @Schema(description = "规格值")
    private String specValue;

    @Schema(description = "规格图片(一级规格必填), 优先级比sku_pic低")
    private String specPic;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "标签级别 1:一级标签 2:二级标签")
    private SpecLevel level;

}
