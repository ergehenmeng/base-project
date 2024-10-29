package com.eghm.service.member.impl;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.cache.CacheService;
import com.eghm.common.EmailService;
import com.eghm.common.MemberTokenService;
import com.eghm.common.SmsService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.email.SendEmail;
import com.eghm.dto.ext.*;
import com.eghm.dto.login.AccountLoginDTO;
import com.eghm.dto.login.SmsLoginDTO;
import com.eghm.dto.member.*;
import com.eghm.dto.register.AccountRegisterDTO;
import com.eghm.dto.register.MobileRegisterDTO;
import com.eghm.enums.*;
import com.eghm.exception.BusinessException;
import com.eghm.exception.DataException;
import com.eghm.handler.chain.HandlerChain;
import com.eghm.handler.chain.MessageData;
import com.eghm.handler.chain.annotation.HandlerEnum;
import com.eghm.mapper.MemberMapper;
import com.eghm.model.LoginDevice;
import com.eghm.model.Member;
import com.eghm.mq.service.MessageService;
import com.eghm.service.member.LoginService;
import com.eghm.service.member.MemberService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DateUtil;
import com.eghm.utils.RegExpUtil;
import com.eghm.utils.StringUtil;
import com.eghm.vo.login.LoginTokenVO;
import com.eghm.vo.member.MemberResponse;
import com.eghm.vo.member.MemberVO;
import com.eghm.wechat.WeChatMiniService;
import com.eghm.wechat.WeChatMpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2019/8/19 15:50
 */
@Service("memberService")
@Slf4j
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final Encoder encoder;

    private final SmsService smsService;

    private final HandlerChain handlerChain;

    private final LoginService loginService;

    private final EmailService emailService;

    private final SysConfigApi sysConfigApi;

    private final MemberMapper memberMapper;

    private final CacheService cacheService;

    private final MessageService messageService;

    private final WeChatMpService weChatMpService;

    private final WeChatMiniService weChatMiniService;

    private final MemberTokenService memberTokenService;

    @Override
    public Page<MemberResponse> getByPage(MemberQueryRequest request) {
        return memberMapper.listPage(request.createPage(), request);
    }

    @Override
    public List<MemberResponse> getList(MemberQueryRequest request) {
        Page<MemberResponse> listPage = memberMapper.listPage(request.createNullPage(), request);
        return listPage.getRecords();
    }

    @Override
    public Member doRegister(MemberRegister register) {
        Member member = DataUtil.copy(register, Member.class);
        member.setId(IdWorker.getId());
        member.setCreateDate(LocalDate.now());
        member.setCreateMonth(LocalDate.now().format(DateUtil.MIN_FORMAT));
        member.setInviteCode(StringUtil.encryptNumber(member.getId()));
        if (StrUtil.isBlank(member.getNickName())) {
            member.setNickName(sysConfigApi.getString(ConfigConstant.NICK_NAME_PREFIX) + System.nanoTime());
        }
        memberMapper.insert(member);
        this.registerPostHandler(member, register);
        return member;
    }

    @Override
    public LoginTokenVO accountLogin(AccountLoginDTO login) {
        Member member = this.getByAccount(login.getAccount());
        if (member == null || !encoder.match(MD5.create().digestHex(login.getPwd()), member.getPwd())) {
            throw new BusinessException(ErrorCode.MEMBER_PASSWORD_ERROR);
        }
        this.checkMemberLock(member);
        RequestMessage request = ApiHolder.get();
        LoginDevice loginLog = loginService.getBySerialNumber(member.getId(), request.getSerialNumber());
        if (loginLog == null && StrUtil.isNotBlank(member.getMobile())) {
            // 新设备登陆时,如果使用密码登陆需要验证短信,当然前提是用户已经绑定手机号码
            throw new DataException(ErrorCode.NEW_DEVICE_LOGIN, member.getMobile());
        }
        return this.doLogin(member, login.getIp());
    }

    @Override
    public LoginTokenVO smsLogin(SmsLoginDTO login) {
        Member member = this.getByAccountRequired(login.getMobile());
        this.checkMemberLock(member);
        smsService.verifySmsCode(TemplateType.MEMBER_LOGIN, login.getMobile(), login.getSmsCode());
        return this.doLogin(member, login.getIp());
    }

    @Override
    public Member getByAccount(String account) {
        if (RegExpUtil.mobile(account)) {
            return this.getByMobile(account);
        }
        return this.getByEmail(account);
    }

    @Override
    public void updateState(Long memberId, Boolean state) {
        Member member = new Member();
        member.setId(memberId);
        member.setState(state);
        memberMapper.updateById(member);
        if (Boolean.FALSE.equals(member.getState())) {
            this.offline(member.getId());
        }
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
    public void sendForgetSms(String mobile, String ip) {
        Member member = this.getByMobile(mobile);
        if (member == null) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_REGISTER);
        }
        smsService.sendSmsCode(TemplateType.FORGET, member.getMobile(), ip);
    }

    @Override
    public Member getByAccountRequired(String account) {
        Member member = this.getByAccount(account);
        if (member == null) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        return member;
    }

    @Override
    public void registerSendSms(String mobile, String ip) {
        this.mobileRedoVerify(mobile);
        smsService.sendSmsCode(TemplateType.REGISTER, mobile, ip);
    }

    @Override
    public LoginTokenVO registerByMobile(MobileRegisterDTO request) {
        this.mobileRedoVerify(request.getMobile());
        smsService.verifySmsCode(TemplateType.REGISTER, request.getMobile(), request.getSmsCode());
        MemberRegister register = DataUtil.copy(request, MemberRegister.class);
        register.setRegisterIp(request.getIp());
        Member member = this.doRegister(register);
        return this.doLogin(member, register.getRegisterIp());
    }

    @Override
    public LoginTokenVO registerByAccount(AccountRegisterDTO dto) {
        this.accountRedoVerify(dto.getAccount());
        MemberRegister register = new MemberRegister();
        register.setRegisterIp(dto.getIp());
        register.setPwd(encoder.encode(MD5.create().digestHex(dto.getPassword())));
        Member member = this.doRegister(register);
        return this.doLogin(member, register.getRegisterIp());
    }

    @Override
    public void offline(Long memberId) {
        memberTokenService.cleanToken(memberId, null);
    }

    @Override
    public void sendBindEmail(String email, Long memberId) {
        this.checkEmail(email);
        SendEmail sendEmail = new SendEmail();
        sendEmail.setType(EmailType.BIND_EMAIL);
        sendEmail.put("memberId", memberId);
        emailService.sendEmail(sendEmail);
    }

    @Override
    public void bindEmail(BindEmailDTO request) {
        Member member = memberMapper.selectById(request.getMemberId());
        if (StrUtil.isNotBlank(member.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_REDO_BIND);
        }
        VerifyEmailCode emailCode = DataUtil.copy(request, VerifyEmailCode.class);
        emailCode.setEmailType(EmailType.BIND_EMAIL);
        emailService.verifyEmailCode(emailCode);
        member.setEmail(request.getEmail());
        memberMapper.updateById(member);
    }

    /**
     * 查看邮箱是会否被占用
     *
     * @param email 邮箱号
     */
    @Override
    public void checkEmail(String email) {
        LambdaQueryWrapper<Member> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Member::getEmail, email);
        Long count = memberMapper.selectCount(wrapper);
        if (count > 0) {
            log.warn("邮箱号已被占用 email:[{}]", email);
            throw new BusinessException(ErrorCode.EMAIL_OCCUPY_ERROR);
        }
    }

    @Override
    public Member getByEmail(String email) {
        LambdaQueryWrapper<Member> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Member::getEmail, email);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return memberMapper.selectOne(wrapper);
    }

    @Override
    public Member getByMobile(String mobile) {
        LambdaQueryWrapper<Member> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Member::getMobile, mobile);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return memberMapper.selectOne(wrapper);
    }

    @Override
    public void sendChangeEmailSms(Long memberId, String ip) {
        Member member = memberMapper.selectById(memberId);
        if (StrUtil.isBlank(member.getMobile())) {
            log.warn("未绑定手机号,无法发送邮箱验证短信 memberId:[{}]", memberId);
            throw new BusinessException(ErrorCode.MOBILE_NOT_BIND);
        }
        smsService.sendSmsCode(TemplateType.CHANGE_EMAIL, member.getMobile(), ip);
    }

    @Override
    public void sendChangeEmailCode(SendEmailAuthCodeDTO request) {
        Member member = memberMapper.selectById(request.getMemberId());
        smsService.verifySmsCode(TemplateType.CHANGE_EMAIL, member.getMobile(), request.getSmsCode());
        this.checkEmail(request.getEmail());
        SendEmail email = new SendEmail();
        email.setType(EmailType.BIND_EMAIL);
        email.put("memberId", request.getMemberId());
        emailService.sendEmail(email);
    }

    @Override
    public void changeEmail(ChangeEmailDTO request) {
        VerifyEmailCode emailCode = DataUtil.copy(request, VerifyEmailCode.class);
        emailCode.setEmailType(EmailType.CHANGE_EMAIL);
        emailService.verifyEmailCode(emailCode);
        Member member = memberMapper.selectById(request.getMemberId());
        member.setEmail(member.getEmail());
        memberMapper.updateById(member);
    }

    @Override
    public Member getByInviteCode(String inviteCode) {
        LambdaQueryWrapper<Member> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Member::getInviteCode, inviteCode);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return memberMapper.selectOne(wrapper);
    }

    @Override
    public LoginTokenVO mpLogin(String jsCode, String ip) {
        WxOAuth2UserInfo userInfo = weChatMpService.auth2(jsCode);
        Member member = this.getByMpOpenId(userInfo.getOpenid());
        // 为空用户算新增
        if (member == null) {
            member = this.doMpRegister(userInfo, ip);
        }
        return this.doLogin(member, ip);
    }

    @Override
    public LoginTokenVO maLogin(String jsCode, String openId, String ip) {
        String mobile = weChatMiniService.authMobile(jsCode);
        Member member = this.getByMobile(mobile);
        this.checkMemberLock(member);
        // 为空用户算新增
        if (member == null) {
            member = this.doMaRegister(mobile, openId, ip);
        }
        return this.doLogin(member, ip);
    }

    @Override
    public LoginTokenVO maLogin(String openId, String ip) {
        Member member = this.getByMaOpenId(openId);
        if (member == null) {
            log.warn("微信小程序使用openId登录,用户信息不存在 [{}]", openId);
            throw new BusinessException(ErrorCode.MEMBER_REGISTER);
        }
        this.checkMemberLock(member);
        return this.doLogin(member, ip);
    }

    @Override
    public Member getByMpOpenId(String openId) {
        LambdaQueryWrapper<Member> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Member::getMpOpenId, openId);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return memberMapper.selectOne(wrapper);
    }

    @Override
    public Member getByMaOpenId(String openId) {
        LambdaQueryWrapper<Member> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Member::getMaOpenId, openId);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return memberMapper.selectOne(wrapper);
    }

    @Override
    public void setPassword(String requestId, String password) {
        String value = cacheService.getValue(CacheConstant.VERIFY_MOBILE_PREFIX + requestId);
        if (value == null) {
            log.error("短信验证码认证已过期 [{}]", requestId);
            throw new BusinessException(ErrorCode.LOGIN_SMS_CODE_EXPIRE);
        }
        Member member = this.getByMobile(value);
        if (member == null) {
            log.error("验证码手机号不存在 [{}] [{}]", requestId, value);
            throw new BusinessException(ErrorCode.MOBILE_NOT_REGISTER);
        }
        member.setPwd(encoder.encode(MD5.create().digestHex(password)));
        memberMapper.updateById(member);
    }

    @Override
    public MemberVO memberHome(Long memberId) {
        Member member = memberMapper.selectById(memberId);
        MemberVO vo = DataUtil.copy(member, MemberVO.class);
        // 从注册之日起将签到信息放到缓存中
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
        memberMapper.updateById(member);
    }

    /**
     * 检查用户是否被封禁
     *
     * @param member 用户
     */
    private void checkMemberLock(Member member) {
        if (member != null && Boolean.FALSE.equals(member.getState())) {
            log.warn("账号已被封禁,无法登录 [{}]", member.getId());
            throw new BusinessException(ErrorCode.MEMBER_LOGIN_FORBID);
        }
    }

    /**
     * 用户注册后置处理
     *
     * @param member 用户信息
     */
    private void registerPostHandler(Member member, MemberRegister register) {
        // 执行注册后置链
        MessageData data = new MessageData();
        data.setMember(member);
        data.setMemberRegister(register);
        handlerChain.execute(data, HandlerEnum.REGISTER);
    }

    /**
     * 微信公众号用户注册
     *
     * @param info 微信用户信息
     * @param ip   ip
     * @return 用户信息
     */
    private Member doMpRegister(WxOAuth2UserInfo info, String ip) {
        MemberRegister register = new MemberRegister();
        register.setRegisterIp(ip);
        register.setNickName(info.getNickname());
        register.setMpOpenId(info.getOpenid());
        register.setSex(info.getSex());
        register.setUnionId(info.getUnionId());
        register.setAvatar(info.getHeadImgUrl());
        register.setChannel(Channel.WECHAT.name());
        return this.doRegister(register);
    }

    /**
     * 小程序用户用户注册
     *
     * @param mobile 手机号
     * @param openId openId
     * @param ip     ip
     * @return 用户信息
     */
    private Member doMaRegister(String mobile, String openId, String ip) {
        MemberRegister register = new MemberRegister();
        register.setRegisterIp(ip);
        register.setMobile(mobile);
        register.setMaOpenId(openId);
        register.setChannel(Channel.WECHAT.name());
        return this.doRegister(register);
    }

    /**
     * 注册手机号被占用校验
     *
     * @param mobile 手机号
     */
    private void mobileRedoVerify(String mobile) {
        LambdaQueryWrapper<Member> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Member::getMobile, mobile);
        Long count = memberMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("手机号被占用,无法注册用户 [{}]", mobile);
            throw new BusinessException(ErrorCode.MOBILE_REGISTER_REDO);
        }
    }

    /**
     * 账号被占用校验
     *
     * @param account 账号
     */
    private void accountRedoVerify(String account) {
        LambdaQueryWrapper<Member> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Member::getAccount, account);
        Long count = memberMapper.selectCount(wrapper);
        if (count > 0) {
            log.error("账号名被占用,无法注册用户 [{}]", account);
            throw new BusinessException(ErrorCode.ACCOUNT_REGISTER_REDO);
        }
    }

    /**
     * 移动端登陆系统 <br/>
     * 1.清除旧token信息 <br/>
     * 2.创建token <br/>
     * 3.添加登陆日志 <br/>
     *
     * @param member 用户id
     * @param ip     登陆ip
     * @return token信息
     */
    private LoginTokenVO doLogin(Member member, String ip) {
        // 将原用户踢掉
        this.offline(member.getId());
        RequestMessage request = ApiHolder.get();
        // 创建token
        MemberToken memberToken = memberTokenService.createToken(member.getId(), request.getChannel());
        // 记录登陆日志信息
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
        return LoginTokenVO.builder().token(memberToken.getToken()).refreshToken(memberToken.getRefreshToken()).build();
    }
}
