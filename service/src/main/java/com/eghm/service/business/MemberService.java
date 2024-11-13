package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.member.*;
import com.eghm.dto.business.statistics.DateRequest;
import com.eghm.dto.sys.login.AccountLoginDTO;
import com.eghm.dto.sys.login.SmsLoginDTO;
import com.eghm.dto.sys.register.AccountRegisterDTO;
import com.eghm.dto.sys.register.MobileRegisterDTO;
import com.eghm.model.Member;
import com.eghm.vo.business.statistics.MemberRegisterVO;
import com.eghm.vo.business.statistics.MemberStatisticsVO;
import com.eghm.vo.login.LoginTokenVO;
import com.eghm.vo.business.member.MemberResponse;
import com.eghm.vo.business.member.MemberVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2019/8/19 15:50
 */
public interface MemberService {

    /**
     * 分页查询会员信息
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<MemberResponse> getByPage(MemberQueryRequest request);

    /**
     * 分页查询会员信息 导出
     *
     * @param request 查询条件
     * @return 列表
     */
    List<MemberResponse> getList(MemberQueryRequest request);

    /**
     * 账号登陆 邮箱或密码登陆
     *
     * @param login 登陆信息
     * @return 登陆成功后的用户信息
     */
    LoginTokenVO accountLogin(AccountLoginDTO login);

    /**
     * 短信验证码+手机号登陆
     *
     * @param login 登陆信息
     * @return 登陆成功后的用户信息
     */
    LoginTokenVO smsLogin(SmsLoginDTO login);

    /**
     * 更新用户状态
     * 1.更新状态
     * 2.清除用户登陆状态
     *
     * @param memberId 用户id
     * @param state    新状态 true:解冻 false:冻结
     */
    void updateState(Long memberId, Boolean state);

    /**
     * 登陆发送验证码
     *
     * @param mobile 手机号码
     * @param ip     ip地址
     */
    void sendLoginSms(String mobile, String ip);

    /**
     * 忘记密码发送验证码
     *
     * @param mobile 手机号码
     */
    void sendForgetSms(String mobile, String ip);

    /**
     * 注册发送验证码
     *
     * @param mobile 手机号码
     */
    void registerSendSms(String mobile, String ip);

    /**
     * 手机号+验证码注册
     *
     * @param request 手机号及验证码信息
     * @return 注册后直接登陆
     */
    LoginTokenVO registerByMobile(MobileRegisterDTO request);

    /**
     * 根据账号注册会员信息
     *
     * @param dto 账号密码
     * @return 登录信息
     */
    LoginTokenVO registerByAccount(AccountRegisterDTO dto);

    /**
     * 强制将用户踢下线  (仅适用于移动端用户)
     * 1.增加一条用户被踢下线的缓存记录
     * 2.清空之前用户登陆的信息
     *
     * @param memberId memberId
     */
    void offline(Long memberId);

    /**
     * 绑定邮箱 发送邮件验证码 (1)
     *
     * @param email    邮箱
     * @param memberId 用户id
     */
    void sendBindEmail(String email, Long memberId);

    /**
     * 绑定邮箱  (2)
     *
     * @param request 邮箱信息
     */
    void bindEmail(BindEmailDTO request);

    /**
     * 更新邮箱发送短信验证码
     *
     * @param memberId memberId
     * @param ip       ip
     */
    void sendChangeEmailSms(Long memberId, String ip);

    /**
     * 发送更换邮箱的邮件信息(邮件内容为验证码)
     *
     * @param request 前台参数
     */
    void sendChangeEmailCode(SendEmailAuthCodeDTO request);

    /**
     * 换绑邮箱
     *
     * @param request 新邮箱信息
     */
    void changeEmail(ChangeEmailDTO request);

    /**
     * 通过邀请码查询用户信息
     *
     * @param inviteCode 邀请码
     * @return member
     */
    Member getByInviteCode(String inviteCode);

    /**
     * 微信网页授权登陆
     *
     * @param jsCode jsCode
     * @param ip     ip
     * @return 登陆成功的信息
     */
    LoginTokenVO mpLogin(String jsCode, String ip);

    /**
     * 微信小程序授权登陆 (手机号码登录)
     * 注意: 该接口未获取用户的unionId, 如需获取需要前端调用 wx.login拿到jsCode, 后端调用sns/jsCode2session接口获取
     *
     * @param jsCode jsCode 注意:此jsCode仅仅获取手机号, 与获取unionId和openId的jsCode不同
     * @param openId openId
     * @param ip     ip
     * @return 登陆成功的信息
     */
    LoginTokenVO maLogin(String jsCode, String openId, String ip);

    /**
     * 微信小程序授权登陆 (openId登录)
     *
     * @param openId openId
     * @param ip     登录ip
     * @return 登陆成功的信息
     */
    LoginTokenVO maLogin(String openId, String ip);

    /**
     * 设置新密码
     *
     * @param requestId requestId
     * @param password  新密码
     */
    void setPassword(String requestId, String password);

    /**
     * 用户个人中心
     *
     * @param memberId memberId
     * @return 个人基本信息
     */
    MemberVO memberHome(Long memberId);

    /**
     * 更新会员信息
     *
     * @param memberId 会员id
     * @param dto      会员基础信息
     */
    void edit(Long memberId, MemberDTO dto);

    /**
     * 注册性别统计
     *
     * @param request 查询条件
     * @return 统计
     */
    MemberStatisticsVO sexChannel(DateRequest request);

    /**
     * 统计注册人数
     *
     * @param request 统计日期
     * @return 人数
     */
    List<MemberRegisterVO> dayRegister(DateRequest request);
}
