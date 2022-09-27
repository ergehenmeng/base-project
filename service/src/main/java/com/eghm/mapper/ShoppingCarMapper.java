package com.eghm.mapper;

import com.eghm.model.ShoppingCar;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.vo.shopping.ShoppingCarVO;
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
public interface ShoppingCarMapper extends BaseMapper<ShoppingCar> {

    /**
     * 查询用户购物车商品列表
     * @param userId 用户id
     * @return 商品列表
     */
    List<ShoppingCarVO> list(@Param("userId") Long userId);
}
