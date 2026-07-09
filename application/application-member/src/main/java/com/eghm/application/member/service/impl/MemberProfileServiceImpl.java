package com.eghm.application.member.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.eghm.cache.CacheService;
import com.eghm.common.EmailService;
import com.eghm.common.SmsService;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.constants.CacheConstant;
import com.eghm.domain.member.model.Member;
import com.eghm.domain.member.repository.MemberRepository;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.enums.TemplateType;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.dto.business.member.BindEmailDTO;
import com.eghm.dto.business.member.ChangeEmailDTO;
import com.eghm.dto.business.member.MemberDTO;
import com.eghm.dto.business.member.SendEmailAuthCodeDTO;
import com.eghm.dto.ext.VerifyEmailCode;
import com.eghm.dto.operate.email.SendEmail;
import com.eghm.enums.EmailType;
import com.eghm.application.member.service.MemberProfileService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.StringUtil;
import com.eghm.vo.business.member.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.eghm.utils.StringUtil.isBlank;

/**
 * 会员资料服务实现
 *
 * @author 二哥很猛
 * @since 2019/8/19 15:50
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberProfileServiceImpl implements MemberProfileService {

    private final Encoder encoder;
    private final SmsService smsService;
    private final EmailService emailService;
    private final CacheService cacheService;
    private final MemberRepository memberRepository;

    @Override
    public void sendForgetSms(String mobile, String ip) {
        Member member = memberRepository.findByMobile(mobile);
        if (member == null) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_REGISTER);
        }
        smsService.sendSmsCode(TemplateType.FORGET, member.getMobile(), ip);
    }

    @Override
    public void sendBindEmail(String email, Long memberId) {
        this.assertEmailAvailable(email);
        SendEmail sendEmail = new SendEmail();
        sendEmail.setType(EmailType.BIND_EMAIL);
        sendEmail.setTo(email);
        sendEmail.addParam("memberId", memberId);
        emailService.sendEmail(sendEmail);
    }

    @Override
    public void bindEmail(BindEmailDTO request) {
        Member member = memberRepository.findById(request.getMemberId());
        VerifyEmailCode emailCode = DataUtil.copy(request, VerifyEmailCode.class);
        emailCode.setEmailType(EmailType.BIND_EMAIL);
        emailService.verifyEmailCode(emailCode);
        member.bindEmail(request.getEmail());
        memberRepository.update(member);
    }

    @Override
    public void sendChangeEmailSms(Long memberId, String ip) {
        Member member = memberRepository.findById(memberId);
        if (isBlank(member.getMobile())) {
            log.warn("未绑定手机号,无法发送邮箱验证短信 memberId:[{}]", memberId);
            throw new BusinessException(ErrorCode.MOBILE_NOT_BIND);
        }
        smsService.sendSmsCode(TemplateType.CHANGE_EMAIL, member.getMobile(), ip);
    }

    @Override
    public void sendChangeEmailCode(SendEmailAuthCodeDTO request) {
        Member member = memberRepository.findById(request.getMemberId());
        smsService.verifySmsCode(TemplateType.CHANGE_EMAIL, member.getMobile(), request.getSmsCode());
        this.assertEmailAvailable(request.getEmail());
        SendEmail email = new SendEmail();
        email.setTo(request.getEmail());
        email.setType(EmailType.BIND_EMAIL);
        email.addParam("memberId", request.getMemberId());
        emailService.sendEmail(email);
    }

    @Override
    public void changeEmail(ChangeEmailDTO request) {
        VerifyEmailCode emailCode = DataUtil.copy(request, VerifyEmailCode.class);
        emailCode.setEmailType(EmailType.CHANGE_EMAIL);
        emailService.verifyEmailCode(emailCode);
        Member member = memberRepository.findById(request.getMemberId());
        member.changeEmail(request.getEmail());
        memberRepository.update(member);
    }

    @Override
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

    @Override
    public MemberVO memberHome(Long memberId) {
        Member member = memberRepository.findById(memberId);
        MemberVO vo = DataUtil.copy(member, MemberVO.class);
        long registerDays = ChronoUnit.DAYS.between(member.getCreateTime().toLocalDate(), LocalDate.now());
        String signKey = CacheConstant.MEMBER_SIGN_IN + memberId;
        vo.setSigned(cacheService.getBitmap(signKey, registerDays));
        vo.setMobile(StringUtil.hiddenMobile(vo.getMobile()));
        return vo;
    }

    @Override
    public void edit(Long memberId, MemberDTO dto) {
        Member member = DataUtil.copy(dto, Member.class);
        member.setId(memberId);
        memberRepository.update(member);
    }

    @Override
    public Member getByInviteCode(String inviteCode) {
        return memberRepository.findByInviteCode(inviteCode);
    }

    private void assertEmailAvailable(String email) {
        if (memberRepository.existsByEmail(email)) {
            log.warn("邮箱号已被占用 email:[{}]", email);
            throw new BusinessException(ErrorCode.EMAIL_REDO_BIND);
        }
    }
}
