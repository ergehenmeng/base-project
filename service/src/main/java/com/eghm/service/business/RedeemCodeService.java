package com.eghm.service.business;

import com.eghm.dto.business.redeem.RedeemCodeAddRequest;
import com.eghm.dto.business.redeem.RedeemCodeEditRequest;
import com.eghm.model.RedeemCode;

/**
 * <p>
 * 兑换码配置表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-10
 */
public interface RedeemCodeService {

    /**
     * 添加兑换码配置
     *
     * @param request 兑换码配置
     */
    void create(RedeemCodeAddRequest request);

    /**
     * 修改兑换码配置
     *
     * @param request 兑换码配置
     */
    void update(RedeemCodeEditRequest request);

    /**
     * 删除兑换码配置
     *
     * @param id 兑换码配置ID
     */
    void delete(Long id);

    /**
     * 根据ID查询兑换码配置
     *
     * @param id id
     * @return 查询配置
     */
    RedeemCode selectByIdRequired(Long id);
}
