package com.eghm.application.system.service;

import com.eghm.application.shared.dto.operate.roster.BlackRosterAddRequest;

/**
 * @author 二哥很猛
 * @since 2019/9/9 13:45
 */
public interface BlackRosterApplicationService {

    /**
     * 添加黑名单信息
     *
     * @param request ip及时间
     */
    void addBlackRoster(BlackRosterAddRequest request);

    /**
     * 删除黑名单
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 重新加载黑名单列表
     */
    void reloadBlackRoster();

    /**
     * 是否是需要拦截的ip
     *
     * @param ip ip地址
     * @return true:黑名单 false:白名单
     */
    boolean isInterceptIp(String ip);
}
