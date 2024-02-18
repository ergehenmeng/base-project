package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eghm.dto.business.redeem.RedeemCodeGrantQueryRequest;
import com.eghm.model.RedeemCodeGrant;
import com.eghm.vo.business.redeem.RedeemCodeGrantResponse;

import java.util.List;

/**
 * <p>
 * 兑换码发放表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-10
 */
public interface RedeemCodeGrantService extends IService<RedeemCodeGrant> {

    /**
     * 分页查询兑换码信息
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<RedeemCodeGrantResponse> listPage(RedeemCodeGrantQueryRequest request);

    /**
     * 分页查询兑换码信息 不分页
     *
     * @param request 查询条件
     * @return 列表
     */
    List<RedeemCodeGrantResponse> getList(RedeemCodeGrantQueryRequest request);
}
