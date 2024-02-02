package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eghm.dto.business.venue.VenueSitePriceRequest;
import com.eghm.dto.business.venue.VenueSitePriceUpdateRequest;
import com.eghm.model.VenueSitePrice;

/**
 * <p>
 * 场馆场次价格信息表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */
public interface VenueSitePriceService extends IService<VenueSitePrice> {

    /**
     * 新增或修改场馆场次价格信息
     *
     * @param request 场次价格配置
     */
    void insertOrUpdate(VenueSitePriceRequest request);

    /**
     * 修改场馆场次价格信息
     *
     * @param request 更新价格或预定状态
     */
    void update(VenueSitePriceUpdateRequest request);

    /**
     * 删除某个时间的价格
     *
     * @param id id
     */
    void delete(Long id);
}
