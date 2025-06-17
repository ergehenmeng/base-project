package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.group.GroupBookingQueryDTO;
import com.eghm.dto.business.group.GroupBookingQueryRequest;
import com.eghm.model.GroupBooking;
import com.eghm.vo.business.group.GroupBookingResponse;
import com.eghm.vo.business.group.GroupItemVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 拼团活动表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-23
 */
public interface GroupBookingMapper extends BaseMapper<GroupBooking> {

    /**
     * 分页查询
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<GroupBookingResponse> getByPage(Page<GroupBookingResponse> page, @Param("param") GroupBookingQueryRequest request);

    /**
     * 获取拼团
     *
     * @param bookingId 活动id
     * @return 列表
     */
    GroupBooking getValidById(@Param("bookingId") Long bookingId);

    /**
     * 获取拼团
     *
     * @param bookingId 活动id
     * @return 列表
     */
    GroupBooking getById(@Param("bookingId") Long bookingId);

    /**
     * 分页查询拼团活动 移动端
     *
     * @param page 分页信息
     * @param dto 查询条件
     * @return 列表
     */
    Page<GroupItemVO> listPage(Page<GroupItemVO> page, @Param("param") GroupBookingQueryDTO dto);

    /**
     * 根据商品id查询其是否参加拼团活动 (正在参加的活动)
     *
     * @param itemId 商品id
     * @param merchantId 商户id
     * @return 活动信息
     */
    GroupBooking getByItemId(@Param("itemId") Long itemId, @Param("merchantId") Long merchantId);

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
     * @return 1
     */
    int countJoiningList(@Param("itemIds") List<Long> itemIds, @Param("merchantId") Long merchantId);
}
