package com.eghm.service.business;

import com.eghm.dto.business.venue.VenueSiteAddRequest;
import com.eghm.dto.business.venue.VenueSiteEditRequest;
import com.eghm.enums.ref.State;
import com.eghm.model.VenueSite;
import com.eghm.vo.business.venue.VenueSiteVO;

import java.util.List;

/**
 * <p>
 * 场地信息表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */
public interface VenueSiteService {

    /**
     * 新增场地信息
     *
     * @param request 场地信息
     */
    void create(VenueSiteAddRequest request);

    /**
     * 更新场地信息
     *
     * @param request 场地信息
     */
    void update(VenueSiteEditRequest request);

    /**
     * 商品排序
     *
     * @param id     商品id
     * @param sortBy 排序 最大999
     */
    void sortBy(Long id, Integer sortBy);

    /**
     * 更新上下架状态
     *
     * @param id    id
     * @param state 状态
     */
    void updateState(Long id, State state);

    /**
     * 根据id删除场地信息
     *
     * @param id 场地id
     */
    void delete(Long id);

    /**
     * 查询场地信息
     *
     * @param id ID
     * @return 场地信息
     */
    VenueSite selectByIdRequired(Long id);

    /**
     * 获取场地信息
     *
     * @param venueId 场地id
     * @return 场地信息
     */
    List<VenueSiteVO> getList(Long venueId);
}
