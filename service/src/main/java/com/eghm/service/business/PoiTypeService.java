package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.poi.PoiTypeAddRequest;
import com.eghm.dto.poi.PoiTypeEditRequest;
import com.eghm.dto.poi.PoiTypeQueryRequest;
import com.eghm.vo.poi.PoiTypeResponse;

import java.util.List;

/**
 * <p>
 * poi类型表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-20
 */
public interface PoiTypeService {

    /**
     * 分页查询poi类型
     * @param query 查询条件
     * @return 列表
     */
    Page<PoiTypeResponse> getByPage(PoiTypeQueryRequest query);

    /**
     * 创建poi类型
     * @param request poi类型
     */
    void create(PoiTypeAddRequest request);

    /**
     * 更新poi类型
     * @param request poi类型
     */
    void update(PoiTypeEditRequest request);

    /**
     * 删除类型
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 根据区域查询poi类型
     * @param areaCode 区域
     * @return 列表
     */
    List<PoiTypeResponse> getList(String areaCode);

}
