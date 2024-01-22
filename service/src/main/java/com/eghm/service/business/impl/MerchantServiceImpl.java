package com.eghm.service.business.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.business.merchant.MerchantAddRequest;
import com.eghm.dto.business.merchant.MerchantAuthDTO;
import com.eghm.dto.business.merchant.MerchantEditRequest;
import com.eghm.dto.business.merchant.MerchantQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.RoleMapping;
import com.eghm.enums.SmsType;
import com.eghm.enums.ref.RoleType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.MerchantMapper;
import com.eghm.model.Merchant;
import com.eghm.model.SysUser;
import com.eghm.service.business.MerchantInitService;
import com.eghm.service.business.MerchantService;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.SmsService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.service.sys.SysUserService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.service.wechat.WeChatMiniService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.merchant.MerchantAuthResponse;
import com.eghm.vo.business.merchant.MerchantAuthVO;
import com.eghm.vo.business.merchant.MerchantResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.eghm.constant.CacheConstant.MERCHANT_AUTH_CODE;

/**
 * @author 殿小二
 * @since 2022/5/27
 */
@Service("merchantService")
@AllArgsConstructor
@Slf4j
public class MerchantServiceImpl implements MerchantService {

    private final MerchantMapper merchantMapper;

    private final SysConfigApi sysConfigApi;

    private final Encoder encoder;

    private final SysUserService sysUserService;

    private final SysRoleService sysRoleService;

    private final CacheService cacheService;

    private final List<MerchantInitService> initList;

    private final SmsService smsService;

    private final WeChatMiniService weChatMiniService;

    @Override
    public Page<MerchantResponse> getByPage(MerchantQueryRequest request) {
        return merchantMapper.listPage(request.createPage(), request);
    }

    @Override
    public List<MerchantResponse> getList(MerchantQueryRequest request) {
        Page<MerchantResponse> listPage = merchantMapper.listPage(request.createNullPage(), request);
        return listPage.getRecords();
    }

    @Override
    public void create(MerchantAddRequest request) {
        this.checkMerchantRedo(request.getMerchantName(), null);
        this.checkMobileRedo(request.getMobile(), null);

        String pwd = sysConfigApi.getString(ConfigConstant.MERCHANT_PWD);
        SysUser user = new SysUser();
        user.setUserType(SysUser.USER_TYPE_2);
        String encode = encoder.encode(MD5.create().digestHex(pwd));
        user.setInitPwd(encode);
        user.setPwd(encode);
        // 采用用户名登录
        user.setMobile(request.getMobile());
        user.setNickName(request.getMerchantName());
        sysUserService.insert(user);

        Merchant merchant = DataUtil.copy(request, Merchant.class);
        BigDecimal platformServiceRate = BigDecimal.valueOf(sysConfigApi.getDouble(ConfigConstant.PLATFORM_SERVICE_RATE, 5D));
        merchant.setPlatformServiceRate(platformServiceRate);
        // 系统用户和商户关联
        merchant.setUserId(user.getId());
        merchantMapper.insert(merchant);

        List<RoleType> roleTypes = RoleMapping.parseRoleType(request.getType());
        sysRoleService.authRole(merchant.getId(), roleTypes);
        this.initStore(merchant, roleTypes);
    }

    @Override
    public void update(MerchantEditRequest request) {
        this.checkMerchantRedo(request.getMerchantName(), request.getId());
        this.checkMobileRedo(request.getMobile(), request.getId());
        Merchant merchant = DataUtil.copy(request, Merchant.class);
        merchantMapper.updateById(merchant);
        SysUser user = new SysUser();
        user.setMobile(request.getMobile());
        user.setNickName(request.getMerchantName());
        sysUserService.updateById(user);
        sysRoleService.authRole(merchant.getId(), RoleMapping.parseRoleType(request.getType()));
    }

    @Override
    public Merchant selectByUserId(Long userId) {
        LambdaUpdateWrapper<Merchant> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Merchant::getUserId, userId);
        return merchantMapper.selectOne(wrapper);
    }

    @Override
    public Merchant selectByIdRequired(Long id) {
        Merchant merchant = merchantMapper.selectById(id);
        if (merchant == null) {
            log.error("商户信息不存在 [{}]", id);
            throw new BusinessException(ErrorCode.MERCHANT_NULL);
        }
        return merchant;
    }

    @Override
    public void lock(Long id) {
        Merchant merchant = merchantMapper.selectById(id);
        sysUserService.lockUser(merchant.getUserId());
    }

    @Override
    public void unlock(Long id) {
        Merchant merchant = merchantMapper.selectById(id);
        sysUserService.unlockUser(merchant.getUserId());
    }

    @Override
    public void resetPwd(Long id) {
        Merchant merchant = merchantMapper.selectById(id);
        String pwd = sysConfigApi.getString(ConfigConstant.MERCHANT_PWD);
        sysUserService.resetPassword(merchant.getUserId(), pwd);
    }

    @Override
    public void binding(MerchantAuthDTO dto) {
        long merchantId = this.getMerchantId(dto.getAuthCode());
        Merchant merchant = this.selectByIdRequired(merchantId);
        if (StrUtil.isNotBlank(merchant.getOpenId())) {
            log.error("商户已绑定微信,需要先解绑 [{}] [{}] [{}]", merchantId, merchant.getAuthMobile(), merchant.getOpenId());
            throw new BusinessException(ErrorCode.MERCHANT_BINDING);
        }
        merchant.setAuthMobile(dto.getMobile());
        merchant.setOpenId(dto.getOpenId());
        log.info("商户[{}]换绑微信号,旧信息:[{}] [{}], 新信息: [{}] [{}]", merchant.getMerchantName(), merchant.getAuthMobile(), merchant.getOpenId(), dto.getMobile(), dto.getOpenId());
        merchantMapper.updateById(merchant);
    }

    @Override
    public void sendUnbindSms(Long merchantId, String ip) {
        Merchant merchant = this.selectByIdRequired(merchantId);
        if (StrUtil.isBlank(merchant.getAuthMobile())) {
            log.error("商户未绑定微信,无需解绑 [{}]", merchantId);
            throw new BusinessException(ErrorCode.MERCHANT_NO_BIND);
        }
        smsService.sendSmsCode(SmsType.MERCHANT_UNBIND, merchant.getAuthMobile(), ip);
    }

    @Override
    public void unbind(Long merchantId, String smsCode) {
        Merchant merchant = this.selectByIdRequired(merchantId);
        smsService.verifySmsCode(SmsType.MERCHANT_UNBIND, merchant.getAuthMobile(), smsCode);
        log.info("商户[{}]解绑微信号,旧信息:[{}] [{}]", merchant.getMerchantName(), merchant.getAuthMobile(), merchant.getOpenId());
        this.doUnbindMerchant(merchantId);
    }

    @Override
    public void unbind(Long merchantId) {
        Merchant merchant = this.selectByIdRequired(merchantId);
        log.info("商户[{}]解绑微信号,旧信息:[{}] [{}]", merchant.getMerchantName(), merchant.getAuthMobile(), merchant.getOpenId());
        this.doUnbindMerchant(merchantId);
    }

    @Override
    public void adjustRate(Long merchantId, BigDecimal rate) {
        LambdaUpdateWrapper<Merchant> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Merchant::getId, merchantId);
        wrapper.set(Merchant::getPlatformServiceRate, rate);
        merchantMapper.update(null, wrapper);
    }

    @Override
    public MerchantAuthResponse generateAuthCode(Long merchantId) {
        long expire = sysConfigApi.getLong(ConfigConstant.MERCHANT_AUTH_CODE_EXPIRE);
        String authCode = IdUtil.fastSimpleUUID();
        LocalDateTime expireTime = LocalDateTime.now().minusSeconds(expire);
        cacheService.setValue(MERCHANT_AUTH_CODE + authCode, merchantId, expire);
        MerchantAuthResponse vo = new MerchantAuthResponse();
        vo.setAuthCode(authCode);
        vo.setExpireTime(expireTime);
        return vo;
    }

    @Override
    public MerchantAuthVO getAuth(String jsCode, String authCode) {
        long merchantId = this.getMerchantId(authCode);
        Merchant merchant = this.selectByIdRequired(merchantId);
        MerchantAuthVO vo = new MerchantAuthVO();
        vo.setMerchantName(merchant.getMerchantName());
        vo.setLegalName(merchant.getLegalName());
        boolean hasBind = StrUtil.isNotBlank(merchant.getAuthMobile());
        vo.setHasBind(hasBind);
        if (hasBind) {
            vo.setAuthMobile(merchant.getAuthMobile());
        } else {
            vo.setAuthMobile(weChatMiniService.authMobile(jsCode));
        }
        return vo;
    }

    /**
     * 根据授权码查询商户id
     *
     * @param authCode 授权码
     * @return 商户信息
     */
    private long getMerchantId(String authCode) {
        String value = cacheService.getValue(MERCHANT_AUTH_CODE + authCode);
        if (StrUtil.isBlank(value)) {
            log.error("授权码已过期,请重新生成");
            throw new BusinessException(ErrorCode.MERCHANT_CODE_EXPIRE);
        }
        return Long.parseLong(value);
    }

    /**
     * 解绑商户微信号
     *
     * @param merchantId 商户ID
     */
    private void doUnbindMerchant(Long merchantId) {
        LambdaUpdateWrapper<Merchant> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(Merchant::getAuthMobile, null);
        wrapper.set(Merchant::getOpenId, null);
        wrapper.eq(Merchant::getId, merchantId);
        merchantMapper.update(null, wrapper);
    }

    /**
     * 校验商户名称是否被占用
     *
     * @param merchantName 商户名称
     * @param id           id
     */
    private void checkMerchantRedo(String merchantName, Long id) {
        LambdaQueryWrapper<Merchant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Merchant::getMerchantName, merchantName);
        wrapper.eq(id != null, Merchant::getId, id);
        Long count = merchantMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("商户名称被占用 [{}]", merchantName);
            throw new BusinessException(ErrorCode.MERCHANT_REDO);
        }
    }

    /**
     * 校验手机号是否被占用
     *
     * @param mobile 用户名
     * @param id     用户
     */
    private void checkMobileRedo(String mobile, Long id) {
        LambdaQueryWrapper<Merchant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Merchant::getMobile, mobile);
        wrapper.eq(id != null, Merchant::getId, id);
        Long count = merchantMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("商户手机号被占用 [{}]", mobile);
            throw new BusinessException(ErrorCode.MERCHANT_MOBILE_REDO);
        }
    }

    /**
     * 根据商户信息初始化商户下的店铺信息
     *
     * @param merchant 商户信息
     * @param typeList 角色类型
     */
    private void initStore(Merchant merchant, List<RoleType> typeList) {
        initList.forEach(service -> {
            if (service.support(typeList)) {
                service.init(merchant);
            }
        });
    }

}
