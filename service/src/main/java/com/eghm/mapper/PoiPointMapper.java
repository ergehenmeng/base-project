package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.poi.PoiPointQueryRequest;
import com.eghm.model.PoiPoint;
import com.eghm.vo.poi.BasePointResponse;
import com.eghm.vo.poi.PoiPointResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * poi点位信息 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-20
 */
public interface PoiPointMapper extends BaseMapper<PoiPoint> {

    /**
     * 分页查询
     * @param page 分页参数
     * @param request 查询条件
     * @return 列表
     */
    Page<PoiPointResponse> getByPage(Page<PoiPointResponse> page, @Param("param") PoiPointQueryRequest request);

    /**
     * 查询区域下的所有点位信息
     * @param areaCode 区域code
     * @return 点位信息
     */
    List<BasePointResponse> getList(@Param("areaCode") String areaCode);
}
