package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.merchant.MerchantUserAddRequest;
import com.eghm.dto.business.merchant.MerchantUserEditRequest;
import com.eghm.dto.business.merchant.MerchantUserQueryRequest;
import com.eghm.vo.business.merchant.MerchantUserResponse;

/**
 * @author 二哥很猛
 * @since 2023/9/25
 */
public interface MerchantUserService {

    /**
     * 分页查询商户普通用户列表信息
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<MerchantUserResponse> getByPage(MerchantUserQueryRequest request);

    /**
     * 创建商户用户
     *
     * @param request 用户信息
     */
    void create(MerchantUserAddRequest request);

    /**
     * 更新商户用户信息
     *
     * @param request 用户信息
     */
    void update(MerchantUserEditRequest request);

    /**
     * 主键删除商户用户
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 锁定用户
     *
     * @param id userId
     */
    void lockUser(Long id);

    /**
     * 解锁用户
     *
     * @param id id
     */
    void unlockUser(Long id);

}
