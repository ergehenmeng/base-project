package com.eghm.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.redeem.RedeemCodeQueryRequest;
import com.eghm.model.RedeemCodeGrant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.vo.business.redeem.RedeemCodeGrantResponse;

/**
 * <p>
 * 兑换码发放表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-10
 */
public interface RedeemCodeGrantMapper extends BaseMapper<RedeemCodeGrant> {

    /**
     * 分页查询
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 分页列表
     */
    Page<RedeemCodeGrantResponse> listPage(Page<RedeemCodeGrantResponse> page, RedeemCodeQueryRequest request);
}
