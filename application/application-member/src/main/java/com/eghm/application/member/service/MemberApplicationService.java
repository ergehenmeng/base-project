package com.eghm.application.member.service;

import cn.hutool.core.collection.CollUtil;
import com.eghm.application.member.query.MemberQueryService;
import com.eghm.application.shared.common.SendSmsService;
import com.eghm.application.shared.dto.business.member.BindEmailDTO;
import com.eghm.application.shared.dto.business.member.ChangeEmailDTO;
import com.eghm.application.shared.dto.business.member.MemberDTO;
import com.eghm.application.shared.dto.business.member.SendEmailAuthCodeDTO;
import com.eghm.application.shared.dto.business.member.SendSmsRequest;
import com.eghm.constants.CommonConstant;
import com.eghm.domain.member.repository.MemberRepository;
import com.eghm.domain.shared.enums.TemplateType;
import com.eghm.application.shared.dto.sys.login.AccountLoginDTO;
import com.eghm.application.shared.dto.sys.login.DoubleCheckDTO;
import com.eghm.application.shared.dto.sys.login.SmsLoginDTO;
import com.eghm.application.shared.dto.sys.register.AccountRegisterDTO;
import com.eghm.application.shared.dto.sys.register.MobileRegisterDTO;
import com.eghm.domain.shared.enums.MemberState;
import com.eghm.domain.shared.enums.ScoreType;
import com.eghm.domain.member.model.Member;
import com.eghm.application.shared.vo.business.member.MemberVO;
import com.eghm.application.shared.vo.business.member.SignInVO;
import com.eghm.application.shared.vo.login.LoginTokenVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eghm.application.shared.utils.StringUtil.isBlank;

/**
 * @author 二哥很猛
 * @since 2019/8/19 15:50
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberApplicationService {
    
    private final SendSmsService sendSmsService;
    private final MemberRepository memberRepository;
    private final MemberQueryService memberQueryService;
    private final MemberAuthApplicationService memberAuthService;
    private final MemberScoreApplicationService memberScoreService;
    private final MemberProfileApplicationService memberProfileService;
    private final MemberRegisterApplicationService memberRegisterService;
    
    /**
     * 账号登陆 邮箱或密码登陆
     *
     * @param login 登陆信息
     * @return 登陆成功后的用户信息
     */
    public LoginTokenVO accountLogin(AccountLoginDTO login) {
        return memberAuthService.accountLogin(login);
    }

    /**
     * 账号/邮箱密码登陆后的二次校验
     *
     * @param dto 二次校验信息
     * @return 登陆成功后的用户信息
     */
    public LoginTokenVO doubleCheck(DoubleCheckDTO dto) {
        return memberAuthService.doubleCheck(dto);
    }

    /**
     * 短信验证码+手机号登陆
     *
     * @param login 登陆信息
     * @return 登陆成功后的用户信息
     */
    public LoginTokenVO smsLogin(SmsLoginDTO login) {
        return memberAuthService.smsLogin(login);
    }

    /**
     * 更新用户状态
     * 1.更新状态
     * 2.清除用户登陆状态
     *
     * @param memberId 用户id
     * @param state    新状态 true:解冻 false:冻结
     */
    public void updateState(Long memberId, MemberState state) {
        Member member = memberRepository.findById(memberId);
        member.changeState(state);
        memberRepository.updateState(member.getId(), member.getState());
        if (MemberState.FREEZE == state) {
            memberAuthService.offline(memberId);
        }
    }

    /**
     * 登陆发送验证码
     *
     * @param mobile 手机号码
     * @param ip     ip地址
     */
    public void sendLoginSms(String mobile, String ip) {
        memberAuthService.sendLoginSms(mobile, ip);
    }

    /**
     * 忘记密码发送验证码
     *
     * @param mobile 手机号码
     * @param ip     ip地址
     */
    public void sendForgetSms(String mobile, String ip) {
        memberProfileService.sendForgetSms(mobile, ip);
    }

    /**
     * 注册发送验证码
     *
     * @param mobile 手机号码
     * @param ip     ip地址
     */
    public void registerSendSms(String mobile, String ip) {
        memberRegisterService.registerSendSms(mobile, ip);
    }

    /**
     * 手机号+验证码注册
     *
     * @param request 手机号及验证码信息
     * @return 注册后直接登陆
     */
    public LoginTokenVO registerByMobile(MobileRegisterDTO request) {
        return memberRegisterService.registerByMobile(request);
    }

    /**
     * 根据账号注册会员信息
     *
     * @param dto 账号密码
     * @return 登录信息
     */
    public LoginTokenVO registerByAccount(AccountRegisterDTO dto) {
        return memberRegisterService.registerByAccount(dto);
    }

    /**
     * 强制将用户踢下线  (仅适用于移动端用户)
     * 1.增加一条用户被踢下线的缓存记录
     * 2.清空之前用户登陆的信息
     *
     * @param memberId memberId
     */
    public void offline(Long memberId) {
        memberAuthService.offline(memberId);
    }
    
    /**
     * 绑定邮箱 发送邮件验证码 (1)
     *
     * @param email    邮箱
     * @param memberId 用户id
     */
    public void sendBindEmail(String email, Long memberId) {
        memberProfileService.sendBindEmail(email, memberId);
    }
    
    /**
     * 绑定邮箱  (2)
     *
     * @param request 邮箱信息
     */
    public void bindEmail(BindEmailDTO request) {
        memberProfileService.bindEmail(request);
    }
    
    /**
     * 更新邮箱发送短信验证码
     *
     * @param memberId memberId
     * @param ip       ip
     */
    public void sendChangeEmailSms(Long memberId, String ip) {
        memberProfileService.sendChangeEmailSms(memberId, ip);
    }
    
    /**
     * 发送更换邮箱的邮件信息(邮件内容为验证码)
     *
     * @param request 前台参数
     */
    public void sendChangeEmailCode(SendEmailAuthCodeDTO request) {
        memberProfileService.sendChangeEmailCode(request);
    }
    
    /**
     * 换绑邮箱
     *
     * @param request 新邮箱信息
     */
    public void changeEmail(ChangeEmailDTO request) {
        memberProfileService.changeEmail(request);
    }
    
    /**
     * 通过邀请码查询用户信息
     *
     * @param inviteCode 邀请码
     * @return member
     */
    public Member getByInviteCode(String inviteCode) {
        return memberProfileService.getByInviteCode(inviteCode);
    }
    
    /**
     * 微信网页授权登陆
     *
     * @param jsCode jsCode
     * @param ip     ip
     * @return 登陆成功的信息
     */
    public LoginTokenVO mpLogin(String jsCode, String ip) {
        return memberRegisterService.mpLogin(jsCode, ip);
    }
    
    /**
     * 微信小程序授权登陆 (手机号码登录)
     * 注意: 该接口未获取用户的unionId, 如需获取需要前端调用 wx.login拿到jsCode, 后端调用sns/jsCode2session接口获取
     *
     * @param jsCode jsCode 注意:此jsCode仅仅获取手机号, 与获取unionId和openId的jsCode不同
     * @param openId openId
     * @param ip     ip
     * @return 登陆成功的信息
     */
    public LoginTokenVO maLogin(String jsCode, String openId, String ip) {
        return memberRegisterService.maLogin(jsCode, openId, ip);
    }
    
    /**
     * 微信小程序授权登陆 (openId登录)
     *
     * @param openId openId
     * @param ip     登录ip
     * @return 登陆成功的信息
     */
    public LoginTokenVO maLogin(String openId, String ip) {
        return memberRegisterService.maLogin(openId, ip);
    }
    
    /**
     * 设置新密码
     *
     * @param requestId requestId
     * @param password  新密码
     */
    public void setPassword(String requestId, String password) {
        memberProfileService.setPassword(requestId, password);
    }

    /**
     * 用户个人中心
     *
     * @param memberId memberId
     * @return 个人基本信息
     */
    public MemberVO memberHome(Long memberId) {
        return memberProfileService.memberHome(memberId);
    }
    
    /**
     * 更新会员信息
     *
     * @param memberId 会员id
     * @param dto      会员基础信息
     */
    public void edit(Long memberId, MemberDTO dto) {
        memberProfileService.edit(memberId, dto);
    }

    /**
     * 发送短信
     *
     * @param request 通知信息
     */
    public void sendSms(SendSmsRequest request) {
        List<String> mobileList = memberQueryService.listMobile(request.getMemberIds());
        if (CollUtil.isEmpty(mobileList)) {
            return;
        }
        if (isBlank(request.getParams())) {
            sendSmsService.sendSms(mobileList, TemplateType.of(request.getTemplateId()));
        } else {
            sendSmsService.sendSms(mobileList, TemplateType.of(request.getTemplateId()), request.getParams().split(
                    CommonConstant.COMMA));
        }
    }

    /**
     * 用户签到
     *
     * @param memberId 用户id
     */
    public void signIn(Long memberId) {
        memberScoreService.signIn(memberId);
    }
    
    /**
     * 获取用户签到信息 只显示当月签到信息
     *
     * @param memberId memberId
     * @return 签到信息
     */
    public SignInVO getSignIn(Long memberId) {
        return memberScoreService.getSignIn(memberId);
    }
    
    /**
     * 更新会员积分
     *
     * @param memberId 用户id
     * @param scoreType 积分类型
     * @param score 积分数量
     * @param remark    备注信息
     */
    public void updateScore(Long memberId, ScoreType scoreType, Integer score, String remark) {
        memberScoreService.updateScore(memberId, scoreType, score, remark);
    }
}
