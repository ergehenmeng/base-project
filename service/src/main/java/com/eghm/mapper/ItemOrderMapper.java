package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.item.ItemOrderQueryDTO;
import com.eghm.model.ItemOrder;
import com.eghm.vo.business.order.item.ItemOrderVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品订单表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-09-05
 */
public interface ItemOrderMapper extends BaseMapper<ItemOrder> {

    /**
     * 查询订单下所有商品的总数量
     * @param orderNo 订单编号
     * @return 总数量
     */
    int getProductNum(@Param("orderNo") String orderNo);

    /**
     * 查询用户零售订单列表
     * @param page 分页信息
     * @param dto 查询条件
     * @return 列表
     */
    Page<ItemOrderVO> getList(Page<ItemOrderVO> page, @Param("param") ItemOrderQueryDTO dto);
}
