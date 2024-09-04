package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.venue.VenueSiteQueryRequest;
import com.eghm.model.VenueSite;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.venue.VenueSiteResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 场地信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */
public interface VenueSiteMapper extends BaseMapper<VenueSite> {

    /**
     * 分页查询场地列表
     *
     * @param page 分页
     * @param request 查询条件
     * @return 场地信息
     */
    Page<VenueSiteResponse> getByPage(Page<VenueSiteResponse> page, @Param("param") VenueSiteQueryRequest request);

    /**
     * 分页查询商品列表
     *
     * @param page 分页
     * @param request 查询条件
     * @return 基础信息列表
     */
    Page<BaseProductResponse> getProductPage(Page<BaseProductResponse> page, @Param("param") BaseProductQueryRequest request);

}
