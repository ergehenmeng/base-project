package com.eghm.service.business;

import com.eghm.dto.poi.PoiAreaAddRequest;
import com.eghm.dto.poi.PoiAreaEditRequest;

/**
 * <p>
 * poi区域表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-20
 */
public interface PoiAreaService {

    /**
     * 新增区域
     * @param request 区域信息
     */
    void create(PoiAreaAddRequest request);

    /**
     * 编辑区域
     * @param request 区域信息
     */
    void update(PoiAreaEditRequest request);

    /**
     * 删除区域
     * @param id id
     */
    void deleteById(Long id);
}
