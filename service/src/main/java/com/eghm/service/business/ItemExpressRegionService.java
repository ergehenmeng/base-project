package com.eghm.service.business;

import com.eghm.dto.business.item.express.ItemExpressRegionRequest;

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
     * @param templateId 模板id
     * @param regionList 价格信息
     */
    void createOrUpdate(Long templateId, List<ItemExpressRegionRequest> regionList);
}
