package com.eghm.platform.config.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.platform.config.dto.BlackRosterAddRequest;
import com.eghm.platform.config.entity.BlackRoster;

/**
 * @author 二哥很猛
 * @since 2019/9/9 13:45
 */
public interface BlackRosterService {

    /**
     * 分页查询黑名单列表
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<BlackRoster> getByPage(PagingQuery request);

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
