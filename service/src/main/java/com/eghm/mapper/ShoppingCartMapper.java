package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.ShoppingCart;
import com.eghm.vo.shopping.ShoppingCartItemVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 购物车表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-23
 */
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

    /**
     * 查询用户购物车商品列表
     * @param memberId 用户id
     * @return 商品列表
     */
    List<ShoppingCartItemVO> getList(@Param("memberId") Long memberId);
}
