package com.eghm.domain.operate.model;

import com.eghm.domain.shared.model.BaseEntity;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 资讯信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class News extends BaseEntity {

    /** 资讯标题 */
    private String title;

    /** 资讯编码 */
    private String code;

    /** 一句话描述信息 */
    private String depict;

    /** 标签列表 */
    private String tagName;

    /** 图集 */
    private String image;

    /** 详细信息 */
    private String content;

    /** 视频 */
    private String video;

    /** 排序 */
    private Integer sort;

    /** 点赞数量 */
    private Integer praiseNum;

    /** 是否支持评论 */
    private Boolean commentSupport;

    /** 状态 false:不显示 true:显示 */
    private Boolean state;

    public void assertCommentSupport() {
        if (Boolean.FALSE.equals(commentSupport)) {
            throw new BusinessException(ErrorCode.NEWS_COMMENT_FORBID);
        }
    }

    public void publish() {
        this.state = true;
    }

    public void unpublish() {
        this.state = false;
    }

    public void increasePraiseNum() {
        this.praiseNum = this.praiseNum == null ? 1 : this.praiseNum + 1;
    }

    public static int praiseDelta(boolean praised) {
        return praised ? 1 : -1;
    }

    public boolean isPublished() {
        return Boolean.TRUE.equals(this.state);
    }
}
