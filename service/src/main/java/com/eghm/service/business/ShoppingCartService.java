package com.eghm.service.business;

import com.eghm.model.dto.business.shopping.AddCartDTO;
import com.eghm.model.vo.shopping.ShoppingCartVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/23
 */
public interface ShoppingCartService {

    /**
     * 添加购物车
     * @param dto 商品信息
     */
    void add(AddCartDTO dto);

    /**
     * 查询用户购物车商品列表
     * @param userId 用户id
     * @return 商品列表
     */
    List<ShoppingCartVO> getList(Long userId);

    /**
     * 删除购物车商品
     * @param id 购物车id
     * @param userId 用户id
     */
    void delete(Long id, Long userId);

    /**
     * 更新购物车商品数量
     * @param id 购物车id
     * @param quantity 新数量
     * @param userId 用户id
     */
    void updateQuantity(Long id, Integer quantity, Long userId);
}
