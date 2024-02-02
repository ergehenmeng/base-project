package com.eghm.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.venue.VenueQueryDTO;
import com.eghm.dto.business.venue.VenueQueryRequest;
import com.eghm.model.Venue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.vo.business.venue.VenueResponse;
import com.eghm.vo.business.venue.VenueVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 场馆信息 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */
public interface VenueMapper extends BaseMapper<Venue> {

    /**
     * 分页查询场馆信息
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<VenueResponse> listPage(Page<VenueResponse> page, @Param("param") VenueQueryRequest request);

    /**
     * 分页查询场馆信息
     *
     * @param page 分页
     * @param dto 查询条件
     * @return 列表
     */
    Page<VenueVO> getByPage(Page<VenueVO> page, @Param("param") VenueQueryDTO dto);
}
