package com.eghm.application.member.service;

import cn.hutool.crypto.SecureUtil;
import com.eghm.application.shared.cache.CacheService;
import com.eghm.application.shared.common.EmailService;
import com.eghm.application.shared.common.SmsService;
import com.eghm.application.shared.configuration.encoder.Encoder;
import com.eghm.application.shared.dto.business.member.BindEmailDTO;
import com.eghm.application.shared.dto.business.member.ChangeEmailDTO;
import com.eghm.application.shared.dto.business.member.MemberDTO;
import com.eghm.application.shared.dto.business.member.SendEmailAuthCodeDTO;
import com.eghm.application.shared.dto.ext.VerifyEmailCode;
import com.eghm.application.shared.dto.operate.email.SendEmail;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.utils.StringUtil;
import com.eghm.application.shared.vo.business.member.MemberVO;
import com.eghm.constants.CacheConstant;
import com.eghm.domain.member.model.Member;
import com.eghm.domain.member.repository.MemberRepository;
import com.eghm.domain.member.service.MemberDomainService;
import com.eghm.domain.shared.enums.EmailType;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.enums.TemplateType;
import com.eghm.domain.shared.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.eghm.application.shared.utils.StringUtil.isBlank;

/**
 * 会员资料服务 - 负责用户资料、邮箱、密码相关操作
 *
 * @author 二哥很猛
 * @since 2019/8/19 15:50
 */
@Slf4j
@Service
@AllArgsConstructor
public class MemberProfileApplicationService {
    
    private final Encoder encoder;
    private final SmsService smsService;
    private final EmailService emailService;
    private final CacheService cacheService;
    private final MemberRepository memberRepository;
    
    private static final MemberDomainService MEMBER_DOMAIN_SERVICE = new MemberDomainService();
    
    public void sendForgetSms(String mobile, String ip) {
        Member member = memberRepository.findByMobile(mobile);
        if (member == null) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_REGISTER);
        }
        smsService.sendSmsCode(TemplateType.FORGET, member.getMobile(), ip);
    }
    
    public void sendBindEmail(String email, Long memberId) {
        MEMBER_DOMAIN_SERVICE.assertEmailAvailable(memberRepository, email);
        SendEmail sendEmail = new SendEmail();
        sendEmail.setType(EmailType.BIND_EMAIL);
        sendEmail.setTo(email);
        sendEmail.addParam("memberId", memberId);
        emailService.sendEmail(sendEmail);
    }
    
    public void bindEmail(BindEmailDTO request) {
        Member member = memberRepository.findById(request.getMemberId());
        VerifyEmailCode emailCode = DataUtil.copy(request, VerifyEmailCode.class);
        emailCode.setEmailType(EmailType.BIND_EMAIL);
        emailService.verifyEmailCode(emailCode);
        member.bindEmail(request.getEmail());
        memberRepository.update(member);
    }
    
    public void sendChangeEmailSms(Long memberId, String ip) {
        Member member = memberRepository.findById(memberId);
        if (isBlank(member.getMobile())) {
            log.warn("未绑定手机号,无法发送邮箱验证短信 memberId:[{}]", memberId);
            throw new BusinessException(ErrorCode.MOBILE_NOT_BIND);
        }
        smsService.sendSmsCode(TemplateType.CHANGE_EMAIL, member.getMobile(), ip);
    }
    
    public void sendChangeEmailCode(SendEmailAuthCodeDTO request) {
        Member member = memberRepository.findById(request.getMemberId());
        smsService.verifySmsCode(TemplateType.CHANGE_EMAIL, member.getMobile(), request.getSmsCode());
        MEMBER_DOMAIN_SERVICE.assertEmailAvailable(memberRepository, request.getEmail());
        SendEmail email = new SendEmail();
        email.setTo(request.getEmail());
        email.setType(EmailType.BIND_EMAIL);
        email.addParam("memberId", request.getMemberId());
        emailService.sendEmail(email);
    }
    
    public void changeEmail(ChangeEmailDTO request) {
        VerifyEmailCode emailCode = DataUtil.copy(request, VerifyEmailCode.class);
        emailCode.setEmailType(EmailType.CHANGE_EMAIL);
        emailService.verifyEmailCode(emailCode);
        Member member = memberRepository.findById(request.getMemberId());
        member.changeEmail(request.getEmail());
        memberRepository.update(member);
    }
    
    public void setPassword(String requestId, String password) {
        String value = cacheService.getValue(CacheConstant.VERIFY_MOBILE_PREFIX + requestId);
        if (value == null) {
            log.error("短信验证码认证已过期 [{}]", requestId);
            throw new BusinessException(ErrorCode.LOGIN_SMS_CODE_EXPIRE);
        }
        Member member = memberRepository.findByMobile(value);
        if (member == null) {
            log.error("验证码手机号不存在 [{}] [{}]", requestId, value);
            throw new BusinessException(ErrorCode.MOBILE_NOT_REGISTER);
        }
        member.changePassword(encoder.encode(SecureUtil.sha256(password)));
        memberRepository.update(member);
    }
    
    public MemberVO memberHome(Long memberId) {
        Member member = memberRepository.findById(memberId);
        MemberVO vo = DataUtil.copy(member, MemberVO.class);
        long registerDays = ChronoUnit.DAYS.between(member.getCreateTime().toLocalDate(), LocalDate.now());
        String signKey = CacheConstant.MEMBER_SIGN_IN + memberId;
        vo.setSigned(cacheService.getBitmap(signKey, registerDays));
        vo.setMobile(StringUtil.hiddenMobile(vo.getMobile()));
        return vo;
    }
    
    public void edit(Long memberId, MemberDTO dto) {
        Member member = memberRepository.findById(memberId);
        member.updateProfile(dto.getAvatar(), dto.getNickName(), dto.getSex());
        memberRepository.update(member);
    }
    
    public Member getByInviteCode(String inviteCode) {
        return memberRepository.findByInviteCode(inviteCode);
    }
    
}
