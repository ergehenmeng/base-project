package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 资讯配置
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("news_config")
public class NewsConfig extends BaseEntity {

    @ApiModelProperty(value = "分类标题")
    private String title;

    @ApiModelProperty(value = "资讯编码")
    private String code;

    @ApiModelProperty(value = "是否包含标题")
    private Boolean includeTitle;

    @ApiModelProperty(value = "是否包含标签")
    private Boolean includeTag;

    @ApiModelProperty(value = "是否包含描述信息")
    private Boolean includeDepict;

    @ApiModelProperty(value = "是否包含图集")
    private Boolean includeImage;

    @ApiModelProperty(value = "是否包含详细信息")
    private Boolean includeContent;

    @ApiModelProperty(value = "是否包含视频")
    private Boolean includeVideo;

}
