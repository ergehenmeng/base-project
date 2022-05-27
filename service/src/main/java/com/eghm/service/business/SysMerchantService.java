package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.SysMerchant;
import com.eghm.model.dto.merchant.MerchantAddRequest;
import com.eghm.model.dto.merchant.MerchantEditRequest;
import com.eghm.model.dto.merchant.MerchantQueryRequest;

/**
 * @author 殿小二
 * @date 2022/5/27
 */
public interface SysMerchantService {
    
    /**
     * 分页查询
     * @param request 查询条件
     * @return 列表
     */
    Page<SysMerchant> getByPage(MerchantQueryRequest request);
    
    /**
     * 创建系统商户账号
     * @param request  商户信息
     */
    void create(MerchantAddRequest request);
    
    /**
     * 更细商户信息
     * @param request 账户信息
     */
    void update(MerchantEditRequest request);
    
    /**
     * 账号锁定
     * @param id id
     */
    void lock(Long id);
    
    /**
     * 解锁账号
     * @param id id
     */
    void unlock(Long id);
    
    /**
     * 重置密码
     * @param id id
     */
    void resetPwd(Long id);
}
