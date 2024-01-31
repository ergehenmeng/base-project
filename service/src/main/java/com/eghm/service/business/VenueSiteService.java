package com.eghm.service.business;

import com.eghm.dto.business.venue.VenueSiteAddRequest;
import com.eghm.dto.business.venue.VenueSiteEditRequest;
import com.eghm.model.Venue;
import com.eghm.model.VenueSite;

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
     * 根据id删除场地信息
     *
     * @param id 场地id
     */
    void delete(String id);

    /**
     * 查询场地信息
     *
     * @param id ID
     * @return 场地信息
     */
    VenueSite selectByIdRequired(Long id);
}
