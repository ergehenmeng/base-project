package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.model.ProductStore;
import com.eghm.model.dto.business.product.store.ProductStoreAddRequest;
import com.eghm.model.dto.business.product.store.ProductStoreEditRequest;
import com.eghm.model.dto.business.product.store.ProductStoreQueryRequest;
import com.eghm.model.vo.business.product.store.ProductStoreHomeVO;
import com.eghm.model.vo.business.product.store.ProductStoreVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
public interface ProductStoreService {

    /**
     * 分页查询商铺信息
     * @param request 查询条案件
     * @return 列表
     */
    Page<ProductStore> getByPage(ProductStoreQueryRequest request);

    /**
     * 创建特产店铺
     * @param request 店铺信息
     */
    void create(ProductStoreAddRequest request);

    /**
     * 更新特产店铺
     * @param request 店铺信息
     */
    void update(ProductStoreEditRequest request);

    /**
     * 主键查询
     * @param id id
     * @return 店铺信息
     */
    ProductStore selectById(Long id);

    /**
     * 主键查询, 删除或未上架则抛异常
     * @param id  id
     * @return 店铺信息
     */
    ProductStore selectByIdShelve(Long id);

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
    ProductStoreHomeVO homeDetail(Long id);

    /**
     * 设置为推荐店铺
     * @param id 店铺id
     */
    void setRecommend(Long id);

    /**
     * 推荐店铺列表
     * @return 店铺列表
     */
    List<ProductStoreVO> getRecommend();
}
