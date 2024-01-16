package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.PoiArea;
import com.eghm.vo.poi.PoiAreaResponse;

import java.util.List;

/**
 * <p>
 * poi区域表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-20
 */
public interface PoiAreaMapper extends BaseMapper<PoiArea> {

    /**
     * 全部列表
     *
     * @return 列表
     */
    List<PoiAreaResponse> getList();
}
