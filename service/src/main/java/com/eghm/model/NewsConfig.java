package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "分类标题")
    private String title;

    @Schema(description = "资讯编码")
    private String code;

    @Schema(description = "是否包含标题")
    private Boolean includeTitle;

    @Schema(description = "是否包含描述信息")
    private Boolean includeDepict;

    @Schema(description = "是否包含标签")
    private Boolean includeTag;

    @Schema(description = "是否包含图集")
    private Boolean includeImage;

    @Schema(description = "是否包含详细信息")
    private Boolean includeContent;

    @Schema(description = "是否包含视频")
    private Boolean includeVideo;

}
