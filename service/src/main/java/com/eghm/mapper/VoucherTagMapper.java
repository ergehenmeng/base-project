package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.VoucherTagQueryRequest;
import com.eghm.model.VoucherTag;
import com.eghm.vo.business.restaurant.TagSelectResponse;
import com.eghm.vo.business.restaurant.VoucherTagResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 餐饮标签 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-10-09
 */
public interface VoucherTagMapper extends BaseMapper<VoucherTag> {

    /**
     * 分页查询标签
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<VoucherTagResponse> getByPage(Page<VoucherTagResponse> page, @Param("param") VoucherTagQueryRequest request);

    /**
     * 获取标签列表
     *
     * @param restaurantId 店铺ID
     * @return list
     */
    List<TagSelectResponse> getList(@Param("restaurantId") Long restaurantId);
}
