package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
public class ItemSpec extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    private Long itemId;

    @ApiModelProperty(value = "规格名")
    private String specName;

    @ApiModelProperty(value = "规格名称")
    private String specValue;

    @ApiModelProperty(value = "规格图片(一级规格必填), 优先级比sku_pic低")
    private String specPic;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "标签级别 一级标签 二级标签")
    private Integer level;

}
