package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.poi.PoiAreaAddRequest;
import com.eghm.dto.poi.PoiAreaEditRequest;
import com.eghm.dto.poi.StateRequest;
import com.eghm.vo.poi.BasePoiAreaResponse;
import com.eghm.vo.poi.PoiAreaResponse;

import java.util.List;

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
     * 分页查询区域类别
     *
     * @param query 查询条件
     * @return 列表
     */
    Page<PoiAreaResponse> getByPage(PagingQuery query);

    /**
     * 查询全部区域
     *
     * @return 列表
     */
    List<BasePoiAreaResponse> getList();

    /**
     * 新增区域
     *
     * @param request 区域信息
     */
    void create(PoiAreaAddRequest request);

    /**
     * 编辑区域
     *
     * @param request 区域信息
     */
    void update(PoiAreaEditRequest request);

    /**
     * 编辑区域
     *
     * @param request 区域信息
     */
    void updateState(StateRequest request);

    /**
     * 删除区域
     *
     * @param id id
     */
    void deleteById(Long id);
}
