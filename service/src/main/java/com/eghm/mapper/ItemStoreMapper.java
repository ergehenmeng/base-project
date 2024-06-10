package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.item.store.ItemStoreQueryRequest;
import com.eghm.model.ItemStore;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.item.store.BaseItemStoreResponse;
import com.eghm.vo.business.item.store.ItemStoreResponse;
import com.eghm.vo.business.item.store.ItemStoreVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 店铺信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-01
 */
public interface ItemStoreMapper extends BaseMapper<ItemStore> {

    /**
     * 分页查询列表
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<ItemStoreResponse> getByPage(Page<ItemStoreResponse> page, @Param("param") ItemStoreQueryRequest request);

    /**
     * 店铺推荐列表
     *
     * @param limit 限制多少条
     * @return 列表
     */
    List<ItemStoreVO> getRecommend(@Param("limit") int limit);

    /**
     * 更新评分
     *
     * @param id    id
     * @param score 评分
     */
    void updateScore(@Param("id") Long id, @Param("score") BigDecimal score);

    /**
     * 店铺列表查询
     *
     * @param storeIds 店铺id
     * @return 列表
     */
    List<ItemStoreVO> getList(@Param("storeIds") List<Long> storeIds);

    /**
     * 分页查询列表(含商户信息)
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<BaseStoreResponse> getStorePage(Page<BaseStoreResponse> page, @Param("param") BaseStoreQueryRequest request);

    /**
     * 商户店铺列表
     *
     * @param merchantId 商户id
     * @return 列表
     */
    List<BaseItemStoreResponse> getBaseList(@Param("merchantId") Long merchantId);

    /**
     * 查询列表 (包含商户信息)
     *
     * @param storeIds ids
     * @return 列表
     */
    List<BaseStoreResponse> getStoreList(@Param("storeIds") List<Long> storeIds);
}
