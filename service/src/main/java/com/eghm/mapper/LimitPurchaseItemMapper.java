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

    /**
     * 根据商品id查询其参加的限时购信息
     *
     * @param itemId 商品id
     * @param merchantId 商户id
     * @return 限时购信息
     */
    LimitPurchaseItem getByItemId(@Param("itemId") Long itemId, @Param("merchantId") Long merchantId);

    /**
     * 统计某商品正在参加的拼团活动数量
     *
     * @param itemId 零售id
     * @param merchantId 商户id
     * @return 1
     */
    int countJoining(@Param("itemId") Long itemId, @Param("merchantId") Long merchantId);

    /**
     * 统计某商品正在参加的拼团活动数量
     *
     * @param itemIds 零售id
     * @param merchantId 商户id
     * @param limitId  限时购id
     * @return 1
     */
    int countJoiningList(@Param("itemIds") List<Long> itemIds, @Param("merchantId") Long merchantId, @Param("limitId") Long limitId);
}
