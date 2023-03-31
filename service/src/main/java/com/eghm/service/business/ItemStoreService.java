package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.enums.ref.PlatformState;
import com.eghm.enums.ref.State;
import com.eghm.model.ItemStore;
import com.eghm.dto.business.item.store.ItemStoreAddRequest;
import com.eghm.dto.business.item.store.ItemStoreEditRequest;
import com.eghm.dto.business.item.store.ItemStoreQueryRequest;
import com.eghm.vo.business.item.store.ItemStoreHomeVO;
import com.eghm.vo.business.item.store.ItemStoreVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
public interface ItemStoreService {

    /**
     * 分页查询商铺信息
     * @param request 查询条案件
     * @return 列表
     */
    Page<ItemStore> getByPage(ItemStoreQueryRequest request);

    /**
     * 创建特产店铺
     * @param request 店铺信息
     */
    void create(ItemStoreAddRequest request);

    /**
     * 更新特产店铺
     * @param request 店铺信息
     */
    void update(ItemStoreEditRequest request);

    /**
     * 主键查询
     * @param id id
     * @return 店铺信息
     */
    ItemStore selectByIdRequired(Long id);

    /**
     * 主键查询, 删除或未上架则抛异常
     * @param id  id
     * @return 店铺信息
     */
    ItemStore selectByIdShelve(Long id);

    /**
     * 更新上下架状态
     * @param id id
     * @param state 状态
     */
    void updateState(Long id, State state);

    /**
     * 更新审核状态
     * @param id 房型id
     * @param state 新状态
     */
    void updateAuditState(Long id, PlatformState state);

    /**
     * 店铺首页详情
     * @param id id
     * @return 详情信息
     */
    ItemStoreHomeVO homeDetail(Long id);

    /**
     * 设置为推荐店铺
     * @param id 店铺id
     */
    void setRecommend(Long id);

    /**
     * 推荐店铺列表
     * @return 店铺列表
     */
    List<ItemStoreVO> getRecommend();
}
