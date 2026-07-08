package com.eghm.operate.model;

import com.eghm.common.model.BaseEntity;

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
public class NewsConfig extends BaseEntity {

    /** 分类标题 */
    private String title;

    /** 资讯编码 */
    private String code;

    /** 是否包含标题 */
    private Boolean includeTitle;

    /** 是否包含标签 */
    private Boolean includeTag;

    /** 是否包含描述信息 */
    private Boolean includeDepict;

    /** 是否包含图集 */
    private Boolean includeImage;

    /** 是否包含详细信息 */
    private Boolean includeContent;

    /** 是否包含视频 */
    private Boolean includeVideo;

}
