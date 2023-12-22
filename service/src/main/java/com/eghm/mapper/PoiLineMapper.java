package com.eghm.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.poi.PoiLineQueryRequest;
import com.eghm.model.PoiLine;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.vo.poi.PoiLineResponse;

/**
 * <p>
 * poi线路表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-21
 */
public interface PoiLineMapper extends BaseMapper<PoiLine> {

    /**
     * 分页查询
     * @param page 分页对象
     * @param request 查询参数
     * @return 列表
     */
    Page<PoiLineResponse> getByPage(Page<PoiLineResponse> page, PoiLineQueryRequest request);
}
