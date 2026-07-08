package com.eghm.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.CollectType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * <p>
 * 会员收藏记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-11
 */
@Data
@TableName("member_collect")
@EqualsAndHashCode(callSuper = false)
public class MemberCollectPO extends BaseEntityPO {

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
}
