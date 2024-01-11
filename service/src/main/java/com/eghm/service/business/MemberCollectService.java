package com.eghm.service.business;

import com.eghm.enums.ref.CollectType;

/**
 * <p>
 * 会员收藏记录表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-11
 */
public interface MemberCollectService {

    /**
     * 收藏或取消收藏
     * @param collectId 收藏对象id
     * @param collectType 收藏对象类型
     */
    void collect(Long collectId, CollectType collectType);

    /**
     * 检查会员是否已经收藏该对象, 如果用户未登录,默认没有收藏
     * @param collectId 对象id
     * @param collectType 对象类型
     * @return false : 未收藏 true : 已收藏
     */
    boolean checkCollect(Long collectId, CollectType collectType);
}
