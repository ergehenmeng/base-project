package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.model.Product;
import com.eghm.model.dto.business.product.ProductAddRequest;
import com.eghm.model.dto.business.product.ProductEditRequest;
import com.eghm.model.dto.business.product.ProductQueryRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wyb
 * @date 2022/7/1
 */
public interface ProductService {

    /**
     * 分页查询商品信息
     * @param request 分页信息及查询条件
     * @return 商品列表
     */
    Page<Product> getByPage(ProductQueryRequest request);

    /**
     * 增加商品信息
     * @param request 商品信息
     */
    void create(ProductAddRequest request);

    /**
     * 更新商品信息
     * @param request 特产商品
     */
    void update(ProductEditRequest request);

    /**
     * 主键查询商品信息
     * @param id id
     * @return 商品信息
     */
    Product selectById(Long id);

    /**
     * 主键查询商品信息
     * @param id id
     * @return 商品信息
     */
    Product selectByIdRequired(Long id);

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
     * 根据id查询商品
     * @param ids 商品id
     * @return 商品信息 key为商品id
     */
    Map<Long, Product> getByIds(Set<Long> ids);

    /**
     * 更新销售量
     * @param productNumMap 商品及销售数量
     */
    void updateSaleNum(Map<Long, Integer> productNumMap);

    /**
     * 更新销售量
     * @param id  商品id
     * @param num 销量量-减销量 +增销量
     */
    void updateSaleNum(Long id, Integer num);

    /**
     * 根据订单编号更新订单号下的所有商品销售量
     * @param orderNoList 订单号
     */
    void updateSaleNum(List<String> orderNoList);
}
