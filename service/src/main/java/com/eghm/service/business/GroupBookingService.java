package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.group.GroupBookingAddRequest;
import com.eghm.dto.business.group.GroupBookingEditRequest;
import com.eghm.dto.business.group.GroupBookingQueryRequest;
import com.eghm.vo.business.group.GroupBookingResponse;

/**
 * <p>
 * 拼团活动表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-23
 */
public interface GroupBookingService {

    /**
     * 分页查询拼团活动
     *
     * @param request 分页查询
     * @return 列表
     */
    Page<GroupBookingResponse> getByPage(GroupBookingQueryRequest request);

    /**
     * 新增拼团活动
     *
     * @param request 拼团信息
     */
    void create(GroupBookingAddRequest request);

    /**
     * 修改拼团活动
     *
     * @param request 拼团信息
     */
    void update(GroupBookingEditRequest request);

    /**
     * 删除拼团活动 (拼团中不受影响)
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 获取拼团价格
     *
     * @param skuId skuId
     * @param bookingId 活动id
     * @param salePrice 商品价格
     * @return 价格
     */
    Integer getGroupPrice(Long skuId, Long bookingId, Integer salePrice);
}
