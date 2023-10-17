package com.eghm.service.member;

import com.eghm.dto.ext.MemberRegister;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.login.AccountLoginDTO;
import com.eghm.dto.login.SmsLoginDTO;
import com.eghm.dto.member.*;
import com.eghm.dto.register.RegisterMemberDTO;
import com.eghm.model.Member;
import com.eghm.vo.login.LoginTokenVO;
import com.eghm.vo.member.MemberResponse;
import com.eghm.vo.member.SignInVO;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @date 2019/8/19 15:50
 */
public interface MemberService {

    /**
     * 分页查询会员信息
     * @param request 查询条件
     * @return 列表
     */
    PageData<MemberResponse> getByPage(MemberQueryRequest request);

    /**
     * 主键查询
     * @param memberId id
     * @return member
     */
    Member getById(Long memberId);

    /**
     * 注册新用户,必须保证参数已校验,昵称如果为空默认由系统生成
     * @param register 用户注册信息
     * @return 注册后的用户信息
     */
    Member doRegister(MemberRegister register);

    /**
     * 账号登陆 邮箱或密码登陆
     * @param login 登陆信息
     * @return 登陆成功后的用户信息
     */
    LoginTokenVO accountLogin(AccountLoginDTO login);

    /**
     * 短信验证码+手机号登陆
     * @param login 登陆信息
     * @return 登陆成功后的用户信息
     */
    LoginTokenVO smsLogin(SmsLoginDTO login);

    /**
     * 根据账号查询用户信息
     * @param account 手机号或邮箱
     * @return 用户信息
     */
    Member getByAccount(String account);

    /**
     * 更新用户状态
     * 1.更新状态
     * 2.清除用户登陆状态
     *
     * @param memberId 用户id
     * @param state  新状态 true:解冻 false:冻结
     */
    void updateState(Long memberId, Boolean state);

    /**
     * 登陆发送验证码
     * @param mobile 手机号码
     * @param ip  ip地址
     */
    void sendLoginSms(String mobile, String ip);

    /**
     * 忘记密码发送验证码
     * @param mobile 手机号码
     */
    void sendForgetSms(String mobile, String ip);

    /**
     * 根据账号查询用户信息(如果不存在,则抛异常)
     * @param account 手机号或邮箱
     * @return 用户信息
     */
    Member getByAccountRequired(String account);

    /**
     * 注册发送验证码
     * @param mobile 手机号码
     */
    void registerSendSms(String mobile, String ip);

    /**
     * 手机号+验证码注册
     * @param request 手机号及验证码信息
     * @return 注册后直接登陆
     */
    LoginTokenVO registerByMobile(RegisterMemberDTO request);

    /**
     * 强制将用户踢下线  (仅适用于移动端用户)
     * 1.增加一条用户被踢下线的缓存记录
     * 2.清空之前用户登陆的信息
     * @param memberId memberId
     */
    void offline(Long memberId);

    /**
     * 强制将用户踢下线 (管理后台强制下线用户), 与上面方法区别就是不增加被踢下线的记录
     * 1.清空之前用户登陆的信息
     * @param memberId memberId
     */
    void forceOffline(Long memberId);

    /**
     * 绑定邮箱 发送邮件验证码 (1)
     * @param email  邮箱
     * @param memberId 用户id
     */
    void sendBindEmail(String email, Long memberId);

    /**
     * 绑定邮箱  (2)
     * @param request 邮箱信息
     */
    void bindEmail(BindEmailDTO request);

    /**
     * 用户实名制认证
     * @param request 实名制信息
     */
    void realNameAuth(MemberAuthDTO request);

    /**
     * 查看邮箱是会否被占用
     * @param email 邮箱号
     */
    void checkEmail(String email);

    /**
     * 根据邮箱查询用户信息
     * @param email 邮箱
     * @return 用户信息
     */
    Member getByEmail(String email);

    /**
     * 根据手机号码查询用户
     * @param mobile 手机号
     * @return 用户信息
     */
    Member getByMobile(String mobile);

    /**
     * 更新邮箱发送短信验证码
     * @param memberId memberId
     * @param ip       ip
     */
    void sendChangeEmailSms(Long memberId, String ip);

    /**
     * 发送更换邮箱的邮件信息(邮件内容为验证码)
     * @param request 前台参数
     */
    void sendChangeEmailCode(SendEmailAuthCodeDTO request);

    /**
     * 换绑邮箱
     * @param request 新邮箱信息
     */
    void changeEmail(ChangeEmailDTO request);

    /**
     * 用户签到
     * @param memberId 用户id
     */
    void signIn(Long memberId);

    /**
     * 判断用户今日签到状态
     * @param memberId  用户id
     * @param registerTime 时间
     * @return true 已签到 false 未签到
     */
    Boolean isSignInToday(Long memberId, LocalDateTime registerTime);

    /**
     * 获取用户签到信息 只显示当月签到信息
     * @param memberId memberId
     * @return 签到信息
     */
    SignInVO getSignIn(Long memberId);

    /**
     * 通过邀请码查询用户信息
     * @param inviteCode 邀请码
     * @return member
     */
    Member getByInviteCode(String inviteCode);

    /**
     * 微信网页授权登陆
     * @param jsCode jsCode
     * @param ip     ip
     * @return 登陆成功的信息
     */
    LoginTokenVO mpLogin(String jsCode, String ip);

    /**
     * 根据openId查询用户信息
     * @param openId openId
     * @return 用户信息
     */
    Member getByOpenId(String openId);

    /**
     * 设置新密码
     * @param requestId requestId
     * @param password 新密码
     */
    void setPassword(String requestId, String password);

    /**
     * 判断用户是否连续签到
     * @param memberId 用户id
     * @param signDay 最大签到天数 不能超过32天
     * @return true: 连续签到 false:不连续签到
     */
    boolean checkSeriesSign(Long memberId, int signDay);
}
