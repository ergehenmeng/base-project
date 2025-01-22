package com.eghm.service.business.impl;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.IdUtil;
import static com.eghm.utils.StringUtil.isBlank;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.cache.CacheService;
import com.eghm.common.SmsService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.business.merchant.MerchantAddRequest;
import com.eghm.dto.business.merchant.MerchantAuthDTO;
import com.eghm.dto.business.merchant.MerchantEditRequest;
import com.eghm.dto.business.merchant.MerchantQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.RoleMapping;
import com.eghm.enums.TemplateType;
import com.eghm.enums.UserType;
import com.eghm.enums.ref.RoleType;
import com.eghm.enums.ref.UserState;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.MerchantMapper;
import com.eghm.model.Account;
import com.eghm.model.Merchant;
import com.eghm.model.ScoreAccount;
import com.eghm.model.SysUser;
import com.eghm.service.business.*;
import com.eghm.service.sys.SysRoleService;
import com.eghm.service.sys.SysUserService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.merchant.BaseMerchantResponse;
import com.eghm.vo.business.merchant.MerchantAuthResponse;
import com.eghm.vo.business.merchant.MerchantAuthVO;
import com.eghm.vo.business.merchant.MerchantResponse;
import com.eghm.wechat.WeChatMiniService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.eghm.constants.CacheConstant.MERCHANT_AUTH_CODE;
import static com.eghm.utils.StringUtil.isNotBlank;

/**
 * @author 殿小二
 * @since 2022/5/27
 */
@Service("merchantService")
@AllArgsConstructor
@Slf4j
public class MerchantServiceImpl implements MerchantService {

    private final Encoder encoder;

    private final SmsService smsService;

    private final VenueService venueService;

    private final SysConfigApi sysConfigApi;

    private final CacheService cacheService;

    private final ScenicService scenicService;

    private final AccountService accountService;

    private final SysUserService sysUserService;

    private final SysRoleService sysRoleService;

    private final MerchantMapper merchantMapper;

    private final HomestayService homestayService;

    private final ItemStoreService itemStoreService;

    private final List<MerchantInitService> initList;

    private final RestaurantService restaurantService;

    private final WeChatMiniService weChatMiniService;

    private final ScoreAccountService scoreAccountService;

    private final TravelAgencyService travelAgencyService;

    @Override
    public Page<MerchantResponse> getByPage(MerchantQueryRequest request) {
        return merchantMapper.listPage(request.createPage(), request);
    }
    
    @Override
    public List<BaseMerchantResponse> getList() {
        return merchantMapper.getList(SecurityHolder.getMerchantId());
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
        this.checkCreditRedo(request.getCreditCode(), null);
        SysUser user = new SysUser();
        String encode = encoder.encode(MD5.create().digestHex(request.getMobile().substring(3)));
        user.setInitPwd(encode);
        user.setPwd(encode);
        user.setUserName(request.getAccount());
        user.setUserType(UserType.MERCHANT_ADMIN);
        user.setPwdUpdateTime(LocalDateTime.now());
        // 采用用户名登录
        user.setMobile(request.getAccount());
        user.setNickName(request.getMerchantName());
        sysUserService.insert(user);
        Merchant merchant = DataUtil.copy(request, Merchant.class);
        BigDecimal platformServiceRate = BigDecimal.valueOf(sysConfigApi.getDouble(ConfigConstant.PLATFORM_SERVICE_RATE, 5D));
        merchant.setPlatformServiceRate(platformServiceRate);
        // 系统用户和商户关联
        merchant.setUserId(user.getId());
        merchantMapper.insert(merchant);
        List<RoleType> roleTypes = RoleMapping.parseRoleType(request.getType());
        sysRoleService.authRole(user.getId(), roleTypes);
        this.initStore(merchant, roleTypes);
    }

    @Override
    public void update(MerchantEditRequest request) {
        this.checkMerchantRedo(request.getMerchantName(), request.getId());
        this.checkMobileRedo(request.getMobile(), request.getId());
        this.checkCreditRedo(request.getCreditCode(), request.getId());
        Merchant required = this.selectByIdRequired(request.getId());
        Merchant merchant = DataUtil.copy(request, Merchant.class);
        merchantMapper.updateById(merchant);
        SysUser user = new SysUser();
        user.setUserName(request.getAccount());
        user.setMobile(request.getAccount());
        user.setNickName(request.getMerchantName());
        user.setId(required.getUserId());
        sysUserService.updateById(user);
        sysRoleService.authRole(required.getUserId(), RoleMapping.parseRoleType(request.getType()));
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
        Merchant merchant = this.selectByIdRequired(id);
        sysUserService.updateState(merchant.getUserId(), UserState.LOCK);
    }

    @Override
    public void unlock(Long id) {
        Merchant merchant = this.selectByIdRequired(id);
        sysUserService.updateState(merchant.getUserId(), UserState.NORMAL);
    }

    @Override
    public void resetPwd(Long id) {
        Merchant merchant = this.selectByIdRequired(id);
        sysUserService.resetPassword(merchant.getUserId(), merchant.getMobile().substring(3));
    }

    @Override
    public void binding(MerchantAuthDTO dto) {
        long merchantId = this.getMerchantId(dto.getAuthCode());
        Merchant merchant = this.selectByIdRequired(merchantId);
        if (isNotBlank(merchant.getOpenId())) {
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
        if (isBlank(merchant.getAuthMobile())) {
            log.error("商户未绑定微信,无需解绑 [{}]", merchantId);
            throw new BusinessException(ErrorCode.MERCHANT_NO_BIND);
        }
        smsService.sendSmsCode(TemplateType.MERCHANT_UNBIND, merchant.getAuthMobile(), ip);
    }

    @Override
    public void unbind(Long merchantId, String smsCode) {
        Merchant merchant = this.selectByIdRequired(merchantId);
        smsService.verifySmsCode(TemplateType.MERCHANT_UNBIND, merchant.getAuthMobile(), smsCode);
        log.info("商户自己[{}]解绑微信号,旧信息:[{}] [{}]", merchant.getMerchantName(), merchant.getAuthMobile(), merchant.getOpenId());
        this.doUnbindMerchant(merchantId);
    }

    @Override
    public void unbind(Long merchantId) {
        Merchant merchant = this.selectByIdRequired(merchantId);
        log.info("管理员解绑商户[{}]解绑微信号,旧信息:[{}] [{}]", merchant.getMerchantName(), merchant.getAuthMobile(), merchant.getOpenId());
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
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(expire);
        cacheService.setValue(MERCHANT_AUTH_CODE + authCode, merchantId, expire);
        String path = sysConfigApi.getString(ConfigConstant.MERCHANT_AUTH_PATH);
        byte[] bytes = weChatMiniService.generateQRCode(path, "authCode=" + authCode, 1);
        MerchantAuthResponse vo = new MerchantAuthResponse();
        vo.setAuthCode(ImgUtil.toBase64DataUri(ImgUtil.toImage(bytes), ImgUtil.IMAGE_TYPE_PNG));
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
        boolean hasBind = isNotBlank(merchant.getAuthMobile());
        vo.setHasBind(hasBind);
        if (hasBind) {
            vo.setAuthMobile(merchant.getAuthMobile());
        } else {
            vo.setAuthMobile(weChatMiniService.authMobile(jsCode));
        }
        return vo;
    }

    @Override
    public void logout(Long merchantId) {
        Merchant merchant = merchantMapper.selectById(merchantId);
        if (merchant == null) {
            log.warn("商户可能已经注销过了 [{}]", merchantId);
            return;
        }
        Account account = accountService.getAccount(merchantId);
        if (account.getAmount() != 0 || account.getPayFreeze() != 0 || account.getWithdrawFreeze() != 0) {
            log.error("商户[{}]有资金冻结,无法注销", merchantId);
            throw new BusinessException(ErrorCode.MERCHANT_HAS_FREEZE);
        }
        ScoreAccount scoreAccount = scoreAccountService.getAccount(merchantId);
        if (scoreAccount.getAmount() != 0 || scoreAccount.getPayFreeze() != 0 || scoreAccount.getWithdrawFreeze() != 0) {
            log.error("商户[{}]有积分冻结,无法注销", merchantId);
            throw new BusinessException(ErrorCode.MERCHANT_SCORE_HAS_FREEZE);
        }
        sysUserService.updateState(merchant.getUserId(), UserState.LOGOUT);
        log.info("开始注销零售店铺 [{}]", merchantId);
        itemStoreService.logout(merchantId);
        log.info("开始注销餐饮店铺 [{}]", merchantId);
        restaurantService.logout(merchantId);
        log.info("开始注销场馆 [{}]", merchantId);
        venueService.logout(merchantId);
        log.info("开始注销景区 [{}]", merchantId);
        scenicService.logout(merchantId);
        log.info("开始注销旅游社 [{}]", merchantId);
        travelAgencyService.logout(merchantId);
        log.info("开始注销民宿店铺 [{}]", merchantId);
        homestayService.logout(merchantId);
        log.info("商户注销成功 [{}]", merchantId);
    }

    /**
     * 根据授权码查询商户id
     *
     * @param authCode 授权码
     * @return 商户信息
     */
    private long getMerchantId(String authCode) {
        String value = cacheService.getValue(MERCHANT_AUTH_CODE + authCode);
        if (isBlank(value)) {
            log.warn("授权码已过期,请重新生成 [{}]", authCode);
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
        wrapper.eq(Merchant::getId, merchantId);
        wrapper.set(Merchant::getAuthMobile, null);
        wrapper.set(Merchant::getOpenId, null);
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
        wrapper.ne(id != null, Merchant::getId, id);
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
        wrapper.ne(id != null, Merchant::getId, id);
        Long count = merchantMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("商户手机号被占用 [{}]", mobile);
            throw new BusinessException(ErrorCode.MERCHANT_MOBILE_REDO);
        }
    }

    /**
     * 校验商户社会统一信用代码是否被占用
     *
     * @param creditCode 社会统一信用代码
     * @param id           id
     */
    private void checkCreditRedo(String creditCode, Long id) {
        LambdaQueryWrapper<Merchant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Merchant::getCreditCode, creditCode);
        wrapper.ne(id != null, Merchant::getId, id);
        Long count = merchantMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("商户社会统一信用代码名称被占用 [{}] [{}]", id, creditCode);
            throw new BusinessException(ErrorCode.CREDIT_CODE_REDO);
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
