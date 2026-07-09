package com.eghm.domain.member.model;

import com.eghm.domain.shared.model.BaseEntity;

import com.eghm.domain.shared.enums.CollectType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 会员收藏记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemberCollect extends BaseEntity {

    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    /** 会员id */
    private Long memberId;

    /** 收藏id */
    private Long collectId;

    /** 收藏对象类型 (1::资讯 2:公告) */
    private CollectType collectType;

    /** 0:取消收藏, 1:加入收藏 */
    private Integer state;

    /** 创建日期 */
    private LocalDate createDate;

    /** 创建月份 */
    private String createMonth;

    /**
     * 创建收藏记录.
     *
     * @param memberId    会员id
     * @param collectId   收藏对象id
     * @param collectType 收藏对象类型
     * @param createDate  创建日期
     */
    public void create(Long memberId, Long collectId, CollectType collectType, LocalDate createDate) {
        this.memberId = memberId;
        this.collectId = collectId;
        this.collectType = collectType;
        this.createDate = createDate;
        this.createMonth = createDate.format(MONTH_FORMATTER);
        this.state = 1;
    }

    /**
     * 切换收藏状态.
     *
     * @return true: 已收藏 false: 已取消
     */
    public boolean toggle() {
        if (this.isCollected()) {
            this.state = 0;
            return false;
        }
        this.state = 1;
        return true;
    }

    /**
     * 是否已收藏.
     *
     * @return true: 已收藏
     */
    public boolean isCollected() {
        return Integer.valueOf(1).equals(this.state);
    }
}
