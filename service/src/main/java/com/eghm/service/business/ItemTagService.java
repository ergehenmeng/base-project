package com.eghm.service.business;

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
     * 查询标签列表, 组装成树状结构
     * @return 标签列表
     */
    List<ItemTagResponse> getList();
}
