package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.purchase.LimitPurchaseQueryDTO;
import com.eghm.model.LimitPurchaseItem;
import com.eghm.vo.business.limit.LimitItemResponse;
import com.eghm.vo.business.limit.LimitItemVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 限时购商品表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-26
 */
public interface LimitPurchaseItemMapper extends BaseMapper<LimitPurchaseItem> {

    /**
     * 查询限时购活动配置的商品列表
     *
     * @param page 查询分页对象
     * @param dto 查询参数
     * @return 商品列表
     */
    Page<LimitItemVO> getByPage(Page<LimitItemVO> page, @Param("param") LimitPurchaseQueryDTO dto);

    /**
     * 查询限时购活动配置的商品列表
     *
     * @param limitId 活动id
     * @return 商品列表
     */
    List<LimitItemResponse> getLimitList(@Param("limitId") Long limitId);
}
