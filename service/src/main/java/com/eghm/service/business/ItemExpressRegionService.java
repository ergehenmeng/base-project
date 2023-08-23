package com.eghm.service.business;

import com.eghm.dto.business.item.express.ItemExpressRegionRequest;
import com.eghm.model.ItemExpressRegion;

import java.util.List;

/**
 * <p>
 * 快递模板区域 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-22
 */
public interface ItemExpressRegionService {

    /**
     * 新增或更新快递区域运费配置信息
     * @param expressId 模板id
     * @param regionList 价格信息
     */
    void createOrUpdate(Long expressId, List<ItemExpressRegionRequest> regionList);

    /**
     * 批量查询快递区域价格配置信息
     * @param expressIds 快递模板id
     * @return 配置信息
     */
    List<ItemExpressRegion> getList(List<Long> expressIds);
}
