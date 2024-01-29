package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.limit.LimitPurchaseQueryRequest;
import com.eghm.dto.business.purchase.LimitPurchaseAddRequest;
import com.eghm.dto.business.purchase.LimitPurchaseEditRequest;
import com.eghm.vo.business.limit.LimitPurchaseDetailResponse;
import com.eghm.vo.business.limit.LimitPurchaseResponse;

/**
 * <p>
 * 限时购活动表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-26
 */
public interface LimitPurchaseService {

    /**
     * 分页查询限时购活动
     *
     * @param request 分页查询参数
     * @return 列表
     */
    Page<LimitPurchaseResponse> getByPage(LimitPurchaseQueryRequest request);

    /**
     * 新增限时购活动
     *
     * @param request 活动信息
     */
    void create(LimitPurchaseAddRequest request);

    /**
     * 修改限时购活动
     *
     * @param request 活动信息
     */
    void update(LimitPurchaseEditRequest request);

    /**
     * 删除限时购活动
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 获取限时购活动详情
     *
     * @param id id
     * @return 活动配置信息
     */
    LimitPurchaseDetailResponse detailById(Long id);
}
