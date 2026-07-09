package com.eghm.application.member.service.impl;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.eghm.cache.CacheService;
import com.eghm.common.MemberTokenService;
import com.eghm.common.SmsService;
import com.eghm.configuration.authentication.ApiHolder;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.constants.CacheConstant;
import com.eghm.domain.member.model.LoginDevice;
import com.eghm.domain.member.model.Member;
import com.eghm.domain.member.repository.MemberRepository;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.enums.TemplateType;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.shared.exception.DataException;
import com.eghm.dto.ext.LoginRecord;
import com.eghm.dto.ext.MemberToken;
import com.eghm.dto.ext.RequestMessage;
import com.eghm.dto.sys.login.AccountLoginDTO;
import com.eghm.dto.sys.login.DoubleCheckDTO;
import com.eghm.dto.sys.login.SmsLoginDTO;
import com.eghm.enums.ExchangeQueue;
import com.eghm.application.member.service.LoginService;
import com.eghm.application.member.service.MemberAuthService;
import com.eghm.mq.service.MessageService;
import com.eghm.utils.RegExpUtil;
import com.eghm.vo.login.LoginTokenVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.eghm.utils.StringUtil.isBlank;
import static com.eghm.utils.StringUtil.isNotBlank;

/**
 * 会员认证服务实现
 *
 * @author 二哥很猛
 * @since 2019/8/19 15:50
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberAuthServiceImpl implements MemberAuthService {

    private final Encoder encoder;
    private final SmsService smsService;
    private final LoginService loginService;
    private final MemberRepository memberRepository;
    private final CacheService cacheService;
    private final MemberTokenService memberTokenService;
    private final MessageService messageService;

    @Override
    public LoginTokenVO accountLogin(AccountLoginDTO login) {
        Member member = this.getByAccount(login.getAccount());
        if (member == null || !encoder.match(SecureUtil.sha256(login.getPwd()), member.getPwd())) {
            throw new BusinessException(ErrorCode.MEMBER_PASSWORD_ERROR);
        }
        this.checkMemberLock(member);
        RequestMessage request = ApiHolder.get();
        LoginDevice loginLog = loginService.getBySerialNumber(member.getId(), request.getSerialNumber());
        if (loginLog == null && isNotBlank(member.getMobile())) {
            smsService.sendSmsCode(TemplateType.MEMBER_LOGIN, member.getMobile(), login.getIp());
            String uuid = IdUtil.fastSimpleUUID();
            cacheService.setValue(CacheConstant.NEW_DEVICE_LOGIN + uuid, member.getMobile(), 300);
            throw new DataException(ErrorCode.NEW_DEVICE_LOGIN, member.getMobile());
        }
        return this.doLogin(member, login.getIp());
    }

    @Override
    public LoginTokenVO doubleCheck(DoubleCheckDTO dto) {
        String mobile = cacheService.getValue(CacheConstant.NEW_DEVICE_LOGIN + dto.getUuid());
        if (isBlank(mobile)) {
            log.error("登录信息未查询到 [{}]", dto);
            throw new BusinessException(ErrorCode.LOGIN_NULL);
        }
        Member member = this.getByMobile(mobile);
        this.checkMemberLock(member);
        smsService.verifySmsCode(TemplateType.MEMBER_LOGIN, mobile, dto.getSmsCode());
        cacheService.delete(CacheConstant.NEW_DEVICE_LOGIN + dto.getUuid());
        return this.doLogin(member, dto.getIp());
    }

    @Override
    public LoginTokenVO smsLogin(SmsLoginDTO login) {
        Member member = this.getByAccountRequired(login.getMobile());
        this.checkMemberLock(member);
        smsService.verifySmsCode(TemplateType.MEMBER_LOGIN, login.getMobile(), login.getSmsCode());
        return this.doLogin(member, login.getIp());
    }

    @Override
    public void sendLoginSms(String mobile, String ip) {
        Member member = this.getByMobile(mobile);
        if (member == null) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_REGISTER);
        }
        this.checkMemberLock(member);
        smsService.sendSmsCode(TemplateType.MEMBER_LOGIN, member.getMobile(), ip);
    }

    @Override
    public void offline(Long memberId) {
        memberTokenService.cleanToken(memberId, null);
    }

    @Override
    public LoginTokenVO doLogin(Member member, String ip) {
        this.offline(member.getId());
        RequestMessage request = ApiHolder.get();
        MemberToken memberToken = memberTokenService.createToken(member.getId(), request.getChannel());
        LoginRecord loginRecord = LoginRecord.builder()
                .ip(NetUtil.ipv4ToLong(ip))
                .memberId(member.getId())
                .channel(request.getChannel())
                .deviceBrand(request.getDeviceBrand())
                .deviceModel(request.getDeviceModel())
                .softwareVersion(request.getVersion())
                .serialNumber(request.getSerialNumber())
                .build();
        messageService.send(ExchangeQueue.LOGIN_LOG, loginRecord);
        return new LoginTokenVO(memberToken.getToken(), memberToken.getRefreshToken());
    }

    private void checkMemberLock(Member member) {
        if (member != null) {
            member.assertCanLogin();
        }
    }

    private Member getByAccount(String account) {
        if (RegExpUtil.mobile(account)) {
            return this.getByMobile(account);
        }
        return this.getByEmail(account);
    }

    private Member getByAccountRequired(String account) {
        Member member = this.getByAccount(account);
        if (member == null) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return member;
    }

    private Member getByMobile(String mobile) {
        return memberRepository.findByMobile(mobile);
    }

    private Member getByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
