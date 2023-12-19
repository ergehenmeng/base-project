package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.shopping.AddCartDTO;
import com.eghm.dto.business.shopping.ShoppingCartQueryRequest;
import com.eghm.vo.business.shopping.ShoppingCartResponse;
import com.eghm.vo.business.shopping.ShoppingCartVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/23
 */
public interface ShoppingCartService {

    /**
     * 分页查询购物车商品
     * @param request 查询条件
     * @return 列表
     */
    Page<ShoppingCartResponse> getByPage(ShoppingCartQueryRequest request);

    /**
     * 添加购物车
     * @param dto 商品信息
     */
    void add(AddCartDTO dto);

    /**
     * 查询用户购物车商品列表
     * @param memberId 用户id
     * @return 商品列表
     */
    List<ShoppingCartVO> getList(Long memberId);

    /**
     * 删除购物车商品
     * @param id 购物车id
     * @param memberId 用户id
     */
    void delete(Long id, Long memberId);

    /**
     * 更新购物车商品数量
     * @param id 购物车id
     * @param quantity 新数量
     * @param memberId 用户id
     */
    void updateQuantity(Long id, Integer quantity, Long memberId);
}
