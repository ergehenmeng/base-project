package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.redeem.RedeemCodeAddRequest;
import com.eghm.dto.business.redeem.RedeemCodeEditRequest;
import com.eghm.dto.business.redeem.RedeemCodeQueryRequest;
import com.eghm.model.RedeemCode;
import com.eghm.vo.business.redeem.RedeemDetailResponse;

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
     * 分页查询兑换码配置
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<RedeemCode> listPage(RedeemCodeQueryRequest request);

    /**
     * 添加兑换码配置
     *
     * @param request 兑换码配置
     */
    void create(RedeemCodeAddRequest request);

    /**
     * 修改兑换码配置 已发放的兑换码只能修改使用范围
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
     * 详情
     * @param id id
     * @return 兑换码详情
     */
    RedeemDetailResponse detail(Long id);

    /**
     * 生成兑换码
     *
     * @param id id
     */
    void generate(Long id);

}
