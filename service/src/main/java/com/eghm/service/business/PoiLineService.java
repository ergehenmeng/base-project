package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.poi.PoiLineAddRequest;
import com.eghm.dto.poi.PoiLineEditRequest;
import com.eghm.dto.poi.PoiLineQueryRequest;
import com.eghm.vo.poi.PoiLineResponse;

/**
 * <p>
 * poi线路表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-21
 */
public interface PoiLineService {

    /**
     * 分页查询poi线路
     * @param request 请求参数
     * @return poi线路列表
     */
    Page<PoiLineResponse> getByPage(PoiLineQueryRequest request);

    /**
     * 新增poi线路
     * @param request 请求参数
     */
    void create(PoiLineAddRequest request);

    /**
     * 修改poi线路
     * @param request 请求参数
     */
    void update(PoiLineEditRequest request);

    /**
     * 删除poi线路
     * @param id id
     */
    void deleteById(Long id);
}
