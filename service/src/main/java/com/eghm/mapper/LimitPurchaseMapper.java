package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.purchase.LimitPurchaseQueryRequest;
import com.eghm.model.LimitPurchase;
import com.eghm.vo.business.limit.LimitPurchaseResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 限时购活动表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-26
 */
public interface LimitPurchaseMapper extends BaseMapper<LimitPurchase> {

    /**
     * 分页查询
     *
     * @param page 分页对象
     * @param request 查询条件
     * @return 列表
     */
    Page<LimitPurchaseResponse> getByPage(Page<LimitPurchaseResponse> page, @Param("param") LimitPurchaseQueryRequest request);
}
