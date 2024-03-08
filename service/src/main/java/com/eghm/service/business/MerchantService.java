package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.merchant.MerchantAddRequest;
import com.eghm.dto.business.merchant.MerchantAuthDTO;
import com.eghm.dto.business.merchant.MerchantEditRequest;
import com.eghm.dto.business.merchant.MerchantQueryRequest;
import com.eghm.model.Merchant;
import com.eghm.vo.business.merchant.MerchantAuthResponse;
import com.eghm.vo.business.merchant.MerchantAuthVO;
import com.eghm.vo.business.merchant.MerchantResponse;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 殿小二
 * @since 2022/5/27
 */
public interface MerchantService {

    /**
     * 分页查询
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<MerchantResponse> getByPage(MerchantQueryRequest request);

    /**
     * 分页查询
     *
     * @param request 查询条件
     * @return 列表
     */
    List<MerchantResponse> getList(MerchantQueryRequest request);

    /**
     * 创建系统商户账号
     *
     * @param request 商户信息
     */
    void create(MerchantAddRequest request);

    /**
     * 更细商户信息
     *
     * @param request 账户信息
     */
    void update(MerchantEditRequest request);

    /**
     * 根据id查询商户
     *
     * @param id id
     * @return 商户信息
     */
    Merchant selectByIdRequired(Long id);

    /**
     * 账号锁定
     *
     * @param id id
     */
    void lock(Long id);

    /**
     * 解锁账号
     *
     * @param id id
     */
    void unlock(Long id);

    /**
     * 重置密码
     *
     * @param id id
     */
    void resetPwd(Long id);

    /**
     * 商户授权绑定微信信息
     *
     * @param dto 微信授权信息
     */
    void binding(MerchantAuthDTO dto);

    /**
     * 发送解绑短信
     *
     * @param merchantId 商户id
     * @param ip         ip
     */
    void sendUnbindSms(Long merchantId, String ip);

    /**
     * 解绑商户微信信息 (商户自己操作)
     *
     * @param merchantId 商户id
     * @param smsCode    短信验证码
     */
    void unbind(Long merchantId, String smsCode);

    /**
     * 解绑商户微信信息 (管理员操作)
     *
     * @param merchantId 商户id
     */
    void unbind(Long merchantId);

    /**
     * 调整平台费率
     *
     * @param merchantId 商户id
     * @param rate   费率,单位:%
     */
    void adjustRate(Long merchantId, BigDecimal rate);

    /**
     * 生成授权码
     *
     * @param merchantId 商户id
     * @return 授权码
     */
    MerchantAuthResponse generateAuthCode(Long merchantId);

    /**
     * 获取授权结果信息
     *
     * @param jsCode   jsCode
     * @param authCode 授权码
     * @return 商户信息
     */
    MerchantAuthVO getAuth(String jsCode, String authCode);

    /**
     * 注销商户
     *
     * @param merchantId 商户ID
     */
    void logout(Long merchantId);
}
