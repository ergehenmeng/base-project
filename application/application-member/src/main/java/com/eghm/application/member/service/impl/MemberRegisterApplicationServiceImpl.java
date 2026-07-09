package com.eghm.application.member.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.eghm.application.shared.common.SmsService;
import com.eghm.application.shared.common.SysConfigService;
import com.eghm.application.shared.configuration.encoder.Encoder;
import com.eghm.constants.ConfigConstant;
import com.eghm.domain.member.model.Member;
import com.eghm.domain.member.repository.MemberRepository;
import com.eghm.domain.member.valueobject.MemberRegistrationInfo;
import com.eghm.domain.shared.enums.Channel;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.enums.TemplateType;
import com.eghm.domain.member.event.MemberRegisteredEvent;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.shared.service.IdGenerator;
import com.eghm.application.shared.dto.ext.MemberRegister;
import com.eghm.application.shared.dto.sys.register.AccountRegisterDTO;
import com.eghm.application.shared.dto.sys.register.MobileRegisterDTO;
import com.eghm.application.member.service.MemberAuthApplicationService;
import com.eghm.application.member.service.MemberRegisterApplicationService;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.utils.DateUtil;
import com.eghm.application.shared.utils.StringUtil;
import com.eghm.application.shared.vo.login.LoginTokenVO;
import com.eghm.application.shared.wechat.WeChatMiniService;
import com.eghm.application.shared.wechat.WeChatMpService;
import com.eghm.application.shared.wechat.dto.MpUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.eghm.application.shared.utils.StringUtil.isBlank;

/**
 * 会员注册服务实现
 *
 * @author 二哥很猛
 * @since 2019/8/19 15:50
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberRegisterApplicationServiceImpl implements MemberRegisterApplicationService {

    private final Encoder encoder;
    private final IdGenerator idGenerator;
    private final SmsService smsService;
    private final SysConfigService sysConfigService;
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final WeChatMpService weChatMpService;
    private final WeChatMiniService weChatMiniService;
    private final MemberAuthApplicationService memberAuthService;

    @Override
    public void registerSendSms(String mobile, String ip) {
        this.assertMobileAvailable(mobile);
        smsService.sendSmsCode(TemplateType.REGISTER, mobile, ip);
    }

    @Override
    public LoginTokenVO registerByMobile(MobileRegisterDTO request) {
        this.assertMobileAvailable(request.getMobile());
        smsService.verifySmsCode(TemplateType.REGISTER, request.getMobile(), request.getSmsCode());
        MemberRegister register = DataUtil.copy(request, MemberRegister.class);
        register.setRegisterIp(request.getIp());
        Member member = this.doRegister(register);
        return memberAuthService.doLogin(member, register.getRegisterIp());
    }

    @Override
    public LoginTokenVO registerByAccount(AccountRegisterDTO dto) {
        this.assertAccountAvailable(dto.getAccount());
        MemberRegister register = new MemberRegister();
        register.setRegisterIp(dto.getIp());
        register.setPwd(encoder.encode(SecureUtil.sha256(dto.getPassword())));
        Member member = this.doRegister(register);
        return memberAuthService.doLogin(member, register.getRegisterIp());
    }

    @Override
    public LoginTokenVO mpLogin(String jsCode, String ip) {
        MpUserInfo userInfo = weChatMpService.auth2(jsCode);
        Member member = memberRepository.findByMpOpenId(userInfo.getOpenId());
        if (member == null) {
            member = this.doMpRegister(userInfo, ip);
        }
        return memberAuthService.doLogin(member, ip);
    }

    @Override
    public LoginTokenVO maLogin(String jsCode, String openId, String ip) {
        String mobile = weChatMiniService.authMobile(jsCode);
        Member member = memberRepository.findByMobile(mobile);
        if (member != null) {
            member.assertCanLogin();
        }
        if (member == null) {
            member = this.doMaRegister(mobile, openId, ip);
        }
        return memberAuthService.doLogin(member, ip);
    }

    @Override
    public LoginTokenVO maLogin(String openId, String ip) {
        Member member = memberRepository.findByMaOpenId(openId);
        if (member == null) {
            log.warn("微信小程序使用openId登录,用户信息不存在 [{}]", openId);
            throw new BusinessException(ErrorCode.MEMBER_REGISTER);
        }
        member.assertCanLogin();
        return memberAuthService.doLogin(member, ip);
    }

    private Member doRegister(MemberRegister register) {
        Member member = DataUtil.copy(register, Member.class);
        Long memberId = idGenerator.nextId();
        LocalDate today = LocalDate.now();
        if (isBlank(member.getNickName())) {
            member.setNickName(sysConfigService.getString(ConfigConstant.NICK_NAME_PREFIX) + System.nanoTime());
        }
        member.initializeRegistration(memberId, StringUtil.encryptNumber(memberId), member.getNickName(), today, today.format(DateUtil.MIN_FORMAT));
        memberRepository.save(member);
        this.registerPostHandler(member, register);
        return member;
    }

    private void registerPostHandler(Member member, MemberRegister register) {
        eventPublisher.publishEvent(new MemberRegisteredEvent(member, DataUtil.copy(register, MemberRegistrationInfo.class)));
    }

    private Member doMpRegister(MpUserInfo info, String ip) {
        MemberRegister register = new MemberRegister();
        register.setRegisterIp(ip);
        register.setNickName(info.getNickname());
        register.setMpOpenId(info.getOpenId());
        register.setSex(info.getSex());
        register.setUnionId(info.getUnionId());
        register.setAvatar(info.getHeadImgUrl());
        register.setChannel(Channel.WECHAT.name());
        return this.doRegister(register);
    }

    private Member doMaRegister(String mobile, String openId, String ip) {
        MemberRegister register = new MemberRegister();
        register.setRegisterIp(ip);
        register.setMobile(mobile);
        register.setMaOpenId(openId);
        register.setChannel(Channel.WECHAT.name());
        return this.doRegister(register);
    }

    private void assertMobileAvailable(String mobile) {
        if (memberRepository.existsByMobile(mobile)) {
            log.warn("手机号被占用,无法注册用户 [{}]", mobile);
            throw new BusinessException(ErrorCode.MOBILE_REGISTER_REDO);
        }
    }

    private void assertAccountAvailable(String account) {
        if (memberRepository.existsByAccount(account)) {
            log.warn("账号被占用,无法注册用户 [{}]", account);
            throw new BusinessException(ErrorCode.ACCOUNT_REGISTER_REDO);
        }
    }
}
