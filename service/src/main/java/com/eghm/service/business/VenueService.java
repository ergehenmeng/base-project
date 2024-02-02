package com.eghm.service.business;

import com.eghm.dto.business.venue.VenueAddRequest;
import com.eghm.dto.business.venue.VenueEditRequest;
import com.eghm.dto.business.venue.VenueQueryDTO;
import com.eghm.enums.ref.State;
import com.eghm.model.Venue;
import com.eghm.vo.business.venue.VenueDetailVO;
import com.eghm.vo.business.venue.VenueVO;

import java.util.List;

/**
 * <p>
 * 场馆信息 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */
public interface VenueService {

    /**
     * 新增场馆
     *
     * @param request 场馆信息
     */
    void create(VenueAddRequest request);

    /**
     * 更新场馆信息
     *
     * @param request 场馆信息
     */
    void update(VenueEditRequest request);

    /**
     * 更新上下架状态
     *
     * @param id    id
     * @param state 状态
     */
    void updateState(Long id, State state);

    /**
     * 删除场馆信息
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 查询场馆信息
     *
     * @param id 场馆ID
     * @return 场馆信息
     */
    Venue selectByIdRequired(Long id);

    /**
     * 分页查询场馆信息
     *
     * @param dto 查询条件
     * @return 列表
     */
    List<VenueVO> getByPage(VenueQueryDTO dto);

    /**
     * 获取场馆详情
     *
     * @param id id
     * @return 场馆详情
     */
    VenueDetailVO getDetail(Long id);
}
