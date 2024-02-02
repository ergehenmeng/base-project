package com.eghm.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.venue.VenueSiteQueryRequest;
import com.eghm.model.VenueSite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.vo.business.venue.VenueSiteResponse;

/**
 * <p>
 * 场地信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-31
 */
public interface VenueSiteMapper extends BaseMapper<VenueSite> {

    Page<VenueSiteResponse> getByPage(Page<VenueSiteResponse> page, VenueSiteQueryRequest request);
}
