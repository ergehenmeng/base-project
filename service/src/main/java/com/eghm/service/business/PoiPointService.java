package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.poi.PoiPointAddRequest;
import com.eghm.dto.poi.PoiPointEditRequest;
import com.eghm.dto.poi.PoiPointQueryRequest;
import com.eghm.vo.poi.PoiPointResponse;

/**
 * <p>
 * poi点位信息 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-20
 */
public interface PoiPointService {

    /**
     * 分页查询点位列表
     * @param query 查询条件
     * @return 列表
     */
    Page<PoiPointResponse> getByPage(PoiPointQueryRequest query);

    /**
     * 新增poi点位信息
     * @param request poi点位信息
     */
    void create(PoiPointAddRequest request);

    /**
     * 修改poi点位信息
     * @param request poi点位信息
     */
    void update(PoiPointEditRequest request);

    /**
     * 删除poi点位信息
     * @param id poi点位信息id
     */
    void deleteById(Long id);
}
