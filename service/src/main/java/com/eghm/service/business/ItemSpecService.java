package com.eghm.service.business;

import com.eghm.dto.business.item.sku.ItemSpecRequest;
import com.eghm.model.Item;
import com.eghm.model.ItemSpec;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 殿小二
 * @date 2023/3/6
 */
public interface ItemSpecService {

    /**
     * 插入规格信息, 注意:多规格才会进行添加
     *
     * @param item     零售商品信息
     * @param specList 规格名称
     * @return 规格值, 规格id
     */
    Map<String, Long> insert(Item item, List<ItemSpecRequest> specList);

    /**
     * 更新规格信息, 注意:单规格商品会删除规格, 多规格时才添加
     *
     * @param item     零售商品信息
     * @param specList 规格信息
     * @return 规格值, 规格id
     */
    Map<String, Long> update(Item item, List<ItemSpecRequest> specList);

    /**
     * 查询spu信息
     *
     * @param itemId 商品信息
     * @return spec
     */
    List<ItemSpec> getByItemId(Long itemId);

    /**
     * 查询spu信息 以map显示
     *
     * @param itemIds 商品信息
     * @return 商品spu信息
     */
    Map<Long, ItemSpec> getByIdMap(Set<Long> itemIds);
}
