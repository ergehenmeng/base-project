package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.group.GroupBookingQueryDTO;
import com.eghm.dto.business.group.GroupBookingQueryRequest;
import com.eghm.model.GroupBooking;
import com.eghm.vo.business.group.GroupBookingResponse;
import com.eghm.vo.business.group.GroupItemVO;
import org.apache.ibatis.annotations.Param;

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
}
