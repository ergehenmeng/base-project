package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.venue.VenueQueryDTO;
import com.eghm.dto.business.venue.VenueQueryRequest;
import com.eghm.model.Venue;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.venue.BaseVenueResponse;
import com.eghm.vo.business.venue.VenueResponse;
import com.eghm.vo.business.venue.VenueVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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
     * 查询商户下的场馆列表
     *
     * @param merchantId 商户Id
     * @return 商户信息
     */
    List<BaseVenueResponse> getList(@Param("merchantId") Long merchantId);

    /**
     * 分页查询场馆信息
     *
     * @param page 分页
     * @param dto 查询条件
     * @return 列表
     */
    Page<VenueVO> getByPage(Page<VenueVO> page, @Param("param") VenueQueryDTO dto);

    /**
     * 分页查询场馆列表
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<BaseStoreResponse> getStorePage(Page<BaseStoreResponse> page, @Param("param") BaseStoreQueryRequest request);

    /**
     * 查询场馆列表 (包含商户信息)
     *
     * @param venueIds 场馆ids
     * @return 列表
     */
    List<BaseStoreResponse> getStoreList(@Param("venueIds") List<Long> venueIds);

    /**
     * 更新评分
     *
     * @param id    id
     * @param score 评分
     */
    void updateScore(@Param("id") Long id, @Param("score") BigDecimal score);

}
