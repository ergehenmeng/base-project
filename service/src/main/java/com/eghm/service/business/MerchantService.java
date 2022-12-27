package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.Merchant;
import com.eghm.model.dto.business.merchant.MerchantAddRequest;
import com.eghm.model.dto.business.merchant.MerchantEditRequest;
import com.eghm.model.dto.business.merchant.MerchantQueryRequest;
import com.eghm.model.vo.login.LoginResponse;

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
     * 商户用户登录系统
     * @param userName 用户名称
     * @param pwd 密码
     * @return 登录成功的信息
     */
    LoginResponse login(String userName, String pwd);
    
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
     * 根据账号查询商户信息
     * @param userName 账号名称
     * @return 商户信息
     */
    Merchant selectByUserName(String userName);
}
