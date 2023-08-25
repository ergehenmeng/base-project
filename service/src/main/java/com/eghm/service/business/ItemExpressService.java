package com.eghm.service.business;

import com.eghm.dto.business.item.express.ItemExpressAddRequest;
import com.eghm.dto.business.item.express.ItemExpressEditRequest;
import com.eghm.vo.business.item.express.ItemExpressResponse;
import com.eghm.vo.business.item.express.ItemExpressVO;

import java.util.List;

/**
 * <p>
 * 快递模板表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-22
 */
public interface ItemExpressService {

    /**
     * 查询商户下的物流模板
     * @param merchantId 商户ID
     * @return 模板
     */
    List<ItemExpressResponse> getList(Long merchantId);

    /**
     * 新增快递模板
     * @param request 模板信息
     */
    void create(ItemExpressAddRequest request);

    /**
     * 更新快递模板
     * @param request 模板信息
     */
    void update(ItemExpressEditRequest request);

    /**
     * 查询商品对应的快递模板
     * @param itemIds 商品Id
     * @param storeId 店铺Id
     * @return 快递模板
     */
    List<ItemExpressVO> getExpressList(List<Long> itemIds, Long storeId);

    /**
     * 逻辑删除快递模板
     * @param id 快递id
     */
    void deleteById(Long id);
}
