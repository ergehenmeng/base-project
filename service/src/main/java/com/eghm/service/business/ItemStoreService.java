package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.item.store.ItemStoreAddRequest;
import com.eghm.dto.business.item.store.ItemStoreEditRequest;
import com.eghm.dto.business.item.store.ItemStoreQueryRequest;
import com.eghm.enums.ref.State;
import com.eghm.model.ItemStore;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.item.store.BaseItemStoreResponse;
import com.eghm.vo.business.item.store.ItemStoreHomeVO;
import com.eghm.vo.business.item.store.ItemStoreResponse;
import com.eghm.vo.business.item.store.ItemStoreVO;

import java.util.List;
import java.util.Map;

/**
 * @author 二哥很猛
 * @since 2022/7/1
 */
public interface ItemStoreService {

    /**
     * 分页查询商铺信息
     *
     * @param request 查询条案件
     * @return 列表
     */
    Page<ItemStoreResponse> getByPage(ItemStoreQueryRequest request);

    /**
     * 查询商铺信息 (不分页)
     *
     * @param request 查询条案件
     * @return 列表
     */
    List<ItemStoreResponse> getList(ItemStoreQueryRequest request);

    /**
     * 创建零售店铺
     *
     * @param request 店铺信息
     */
    void create(ItemStoreAddRequest request);

    /**
     * 更新零售店铺
     *
     * @param request 店铺信息
     */
    void update(ItemStoreEditRequest request);

    /**
     * 主键查询
     *
     * @param id id
     * @return 店铺信息
     */
    ItemStore selectByIdRequired(Long id);

    /**
     * 主键查询, 删除或未上架则抛异常
     *
     * @param ids 批量查询
     * @return 店铺信息
     */
    Map<Long, ItemStore> selectByIdShelveMap(List<Long> ids);

    /**
     * 更新上下架状态
     *
     * @param id    id
     * @param state 状态
     */
    void updateState(Long id, State state);

    /**
     * 店铺首页详情
     *
     * @param id id
     * @return 详情信息
     */
    ItemStoreHomeVO homeDetail(Long id);

    /**
     * 推荐店铺列表
     *
     * @return 店铺列表
     */
    List<ItemStoreVO> getRecommend();

    /**
     * 设置为推荐店铺
     *
     * @param id 店铺id
     * @param recommend true: 推荐 false:不推荐
     */
    void setRecommend(Long id, boolean recommend);

    /**
     * 逻辑删除 (只有平台用户能操作)
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 分页查询列表(含商户信息)
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<BaseStoreResponse> getStorePage(BaseStoreQueryRequest request);

    /**
     * 商户店铺列表
     *
     * @param merchantId 商户id
     * @return 列表
     */
    List<BaseItemStoreResponse> getList(Long merchantId);

    /**
     * 注销商户的零售店铺
     *
     * @param merchantId 商户ID
     */
    void logout(Long merchantId);
}
