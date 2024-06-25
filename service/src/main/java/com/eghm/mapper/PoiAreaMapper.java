package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.model.PoiArea;
import com.eghm.vo.poi.BasePoiAreaResponse;
import com.eghm.vo.poi.PoiAreaResponse;
import org.apache.ibatis.annotations.Param;

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
     * 分页查询
     *
     * @param page 分页信息
     * @param query 查询条件
     * @return 列表
     */
    Page<PoiAreaResponse> getByPage(Page<PoiAreaResponse> page, @Param("param") PagingQuery query);

    /**
     * 全部列表
     *
     * @return 列表
     */
    List<BasePoiAreaResponse> getList();
}
