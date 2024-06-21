package com.eghm.service.business;

import com.eghm.dto.business.item.ItemTagAddRequest;
import com.eghm.dto.business.item.ItemTagEditRequest;
import com.eghm.dto.business.item.ItemTagQueryRequest;
import com.eghm.vo.business.item.ItemTagResponse;

import java.util.List;

/**
 * <p>
 * 零售标签 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-08-08
 */
public interface ItemTagService {

    /**
     * 添加标签
     *
     * @param request 标签信息
     */
    void create(ItemTagAddRequest request);

    /**
     * 更新标签信息
     *
     * @param request 标签信息
     */
    void update(ItemTagEditRequest request);

    /**
     * 删除标签<br/>
     * 如果标签已经被产品使用,则不会删除
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 商品排序
     *
     * @param id     商品id
     * @param sortBy 排序 最大999
     */
    void sortBy(String id, Integer sortBy);

    /**
     * 查询标签列表, 组装成树状结构
     *
     * @param request 基础查询条件
     * @return 标签列表
     */
    List<ItemTagResponse> getList(ItemTagQueryRequest request);
}
