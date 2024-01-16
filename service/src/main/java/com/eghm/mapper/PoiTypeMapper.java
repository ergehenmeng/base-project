package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.poi.PoiTypeQueryRequest;
import com.eghm.model.PoiType;
import com.eghm.vo.poi.PoiTypeResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * poi类型表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-20
 */
public interface PoiTypeMapper extends BaseMapper<PoiType> {

    /**
     * 分页查询
     *
     * @param page    分页参数
     * @param request 查询条件
     * @return 列表
     */
    Page<PoiTypeResponse> getByPage(Page<PoiTypeResponse> page, @Param("param") PoiTypeQueryRequest request);

    /**
     * 根据区域查询
     *
     * @param areaCode 区域编号
     * @return 列表
     */
    List<PoiTypeResponse> getList(@Param("areaCode") String areaCode);
}
