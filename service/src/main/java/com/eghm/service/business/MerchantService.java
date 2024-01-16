package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.merchant.MerchantAddRequest;
import com.eghm.dto.business.merchant.MerchantAuthDTO;
import com.eghm.dto.business.merchant.MerchantEditRequest;
import com.eghm.dto.business.merchant.MerchantQueryRequest;
import com.eghm.model.Merchant;
import com.eghm.vo.business.merchant.MerchantResponse;

import java.util.List;

/**
 * @author 殿小二
 * @date 2022/5/27
 */
public interface MerchantService {
    
    /**
     * 分页查询
     * @param request 查询条件
     * @return 列表
     */
    Page<Merchant> getByPage(MerchantQueryRequest request);

    /**
     * 分页查询
     * @param request 查询条件
     * @return 列表
     */
    List<MerchantResponse> getList(MerchantQueryRequest request);
    
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
     * 根据系统用户id查询商户
     * @param userId 系统用户id
     * @return 商户信息
     */
    Merchant selectByUserId(Long userId);

    /**
     * 根据id查询商户
     * @param id id
     * @return 商户信息
     */
    Merchant selectByIdRequired(Long id);

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

    /**
     * 商户授权绑定微信信息
     * @param dto 微信授权信息
     */
    void auth(MerchantAuthDTO dto);
}
