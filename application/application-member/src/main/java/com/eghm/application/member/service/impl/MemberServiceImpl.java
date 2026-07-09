package com.eghm.application.member.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.dto.ext.Page;
import com.eghm.common.SendSmsService;
import com.eghm.constants.CommonConstant;
import com.eghm.domain.member.model.Member;
import com.eghm.domain.member.repository.MemberRepository;
import com.eghm.domain.shared.enums.Channel;
import com.eghm.domain.shared.enums.Gender;
import com.eghm.domain.shared.enums.MemberState;
import com.eghm.domain.shared.enums.ScoreType;
import com.eghm.domain.shared.enums.SelectType;
import com.eghm.domain.shared.enums.TemplateType;
import com.eghm.dto.business.member.BindEmailDTO;
import com.eghm.dto.business.member.ChangeEmailDTO;
import com.eghm.dto.business.member.MemberDTO;
import com.eghm.dto.business.member.MemberQueryRequest;
import com.eghm.dto.business.member.SendEmailAuthCodeDTO;
import com.eghm.dto.business.member.SendSmsRequest;
import com.eghm.dto.business.statistics.DateRequest;
import com.eghm.dto.sys.login.AccountLoginDTO;
import com.eghm.dto.sys.login.DoubleCheckDTO;
import com.eghm.dto.sys.login.SmsLoginDTO;
import com.eghm.dto.sys.register.AccountRegisterDTO;
import com.eghm.dto.sys.register.MobileRegisterDTO;
import com.eghm.application.member.service.MemberAuthService;
import com.eghm.application.member.service.MemberProfileService;
import com.eghm.application.member.service.MemberQueryGateway;
import com.eghm.application.member.service.MemberRegisterService;
import com.eghm.application.member.service.MemberScoreService;
import com.eghm.application.member.service.MemberService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.member.MemberResponse;
import com.eghm.vo.business.member.MemberVO;
import com.eghm.vo.business.member.SignInVO;
import com.eghm.vo.business.statistics.MemberRegisterVO;
import com.eghm.vo.business.statistics.MemberStatisticsVO;
import com.eghm.vo.business.statistics.PieDataVO;
import com.eghm.vo.login.LoginTokenVO;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.eghm.utils.StringUtil.isBlank;

/**
 * 会员服务门面 - 委托给子服务处理具体业务
 *
 * @author 二哥很猛
 * @since 2019/8/19 15:50
 */
@Slf4j
@Service("memberService")
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryGateway memberQueryGateway;
    private final SendSmsService sendSmsService;
    private final MemberAuthService memberAuthService;
    private final MemberRegisterService memberRegisterService;
    private final MemberProfileService memberProfileService;
    private final MemberScoreService memberScoreService;

    @Override
    public Page<MemberResponse> getByPage(MemberQueryRequest request) {
        return memberQueryGateway.listPage(request.createPage(), request);
    }

    @Override
    public List<MemberResponse> getList(MemberQueryRequest request) {
        Page<MemberResponse> listPage = memberQueryGateway.listPage(request.createNullPage(), request);
        return listPage.getRecords();
    }

    @Override
    public LoginTokenVO accountLogin(AccountLoginDTO login) {
        return memberAuthService.accountLogin(login);
    }

    @Override
    public LoginTokenVO doubleCheck(DoubleCheckDTO dto) {
        return memberAuthService.doubleCheck(dto);
    }

    @Override
    public LoginTokenVO smsLogin(SmsLoginDTO login) {
        return memberAuthService.smsLogin(login);
    }

    @Override
    public void updateState(Long memberId, MemberState state) {
        Member member = memberRepository.findById(memberId);
        member.changeState(state);
        memberRepository.updateState(member.getId(), member.getState());
        if (MemberState.FREEZE == state) {
            memberAuthService.offline(memberId);
        }
    }

    @Override
    public void sendLoginSms(String mobile, String ip) {
        memberAuthService.sendLoginSms(mobile, ip);
    }

    @Override
    public void sendForgetSms(String mobile, String ip) {
        memberProfileService.sendForgetSms(mobile, ip);
    }

    @Override
    public void registerSendSms(String mobile, String ip) {
        memberRegisterService.registerSendSms(mobile, ip);
    }

    @Override
    public LoginTokenVO registerByMobile(MobileRegisterDTO request) {
        return memberRegisterService.registerByMobile(request);
    }

    @Override
    public LoginTokenVO registerByAccount(AccountRegisterDTO dto) {
        return memberRegisterService.registerByAccount(dto);
    }

    @Override
    public void offline(Long memberId) {
        memberAuthService.offline(memberId);
    }

    @Override
    public void sendBindEmail(String email, Long memberId) {
        memberProfileService.sendBindEmail(email, memberId);
    }

    @Override
    public void bindEmail(BindEmailDTO request) {
        memberProfileService.bindEmail(request);
    }

    @Override
    public void sendChangeEmailSms(Long memberId, String ip) {
        memberProfileService.sendChangeEmailSms(memberId, ip);
    }

    @Override
    public void sendChangeEmailCode(SendEmailAuthCodeDTO request) {
        memberProfileService.sendChangeEmailCode(request);
    }

    @Override
    public void changeEmail(ChangeEmailDTO request) {
        memberProfileService.changeEmail(request);
    }

    @Override
    public Member getByInviteCode(String inviteCode) {
        return memberProfileService.getByInviteCode(inviteCode);
    }

    @Override
    public LoginTokenVO mpLogin(String jsCode, String ip) {
        return memberRegisterService.mpLogin(jsCode, ip);
    }

    @Override
    public LoginTokenVO maLogin(String jsCode, String openId, String ip) {
        return memberRegisterService.maLogin(jsCode, openId, ip);
    }

    @Override
    public LoginTokenVO maLogin(String openId, String ip) {
        return memberRegisterService.maLogin(openId, ip);
    }

    @Override
    public void setPassword(String requestId, String password) {
        memberProfileService.setPassword(requestId, password);
    }

    @Override
    public MemberVO memberHome(Long memberId) {
        return memberProfileService.memberHome(memberId);
    }

    @Override
    public void edit(Long memberId, MemberDTO dto) {
        memberProfileService.edit(memberId, dto);
    }

    @Override
    public MemberStatisticsVO sexChannel(DateRequest request) {
        List<PieDataVO> statistics = memberQueryGateway.channelStatistics(request.getStartDate(), request.getEndDate());
        List<PieDataVO> channelList = Lists.newArrayListWithCapacity(8);
        Map<String, PieDataVO> voMap = statistics.stream().collect(Collectors.toMap(PieDataVO::getName, Function.identity()));
        for (Channel value : Channel.values()) {
            channelList.add(voMap.getOrDefault(value.name(), new PieDataVO(value.name())));
        }
        List<PieDataVO> sexStatistics = memberQueryGateway.sexStatistics(request.getStartDate(), request.getEndDate());
        Map<String, PieDataVO> sexMap = sexStatistics.stream().collect(Collectors.toMap(PieDataVO::getName, Function.identity()));
        List<PieDataVO> sexList = Lists.newArrayListWithCapacity(4);
        for (Gender value : Gender.values()) {
            sexList.add(sexMap.getOrDefault(value.getName(), new PieDataVO(value.getName())));
        }
        MemberStatisticsVO vo = new MemberStatisticsVO();
        vo.setChannelList(channelList);
        vo.setSexList(sexList);
        return vo;
    }

    @Override
    public List<MemberRegisterVO> dayRegister(DateRequest request) {
        List<MemberRegisterVO> voList = memberQueryGateway.dayRegister(request);
        if (request.getSelectType() == SelectType.YEAR) {
            Map<String, MemberRegisterVO> voMap = voList.stream().collect(Collectors.toMap(MemberRegisterVO::getCreateMonth, Function.identity()));
            return DataUtil.paddingMonth(voMap, request.getStartDate(), request.getEndDate(), MemberRegisterVO::new);
        } else {
            Map<LocalDate, MemberRegisterVO> voMap = voList.stream().collect(Collectors.toMap(MemberRegisterVO::getCreateDate, Function.identity()));
            return DataUtil.paddingDay(voMap, request.getStartDate(), request.getEndDate(), MemberRegisterVO::new);
        }
    }

    @Override
    public void sendSms(SendSmsRequest request) {
        List<String> mobileList = memberQueryGateway.listMobile(request.getMemberIds());
        if (CollUtil.isEmpty(mobileList)) {
            return;
        }
        if (isBlank(request.getParams())) {
            sendSmsService.sendSms(mobileList, TemplateType.of(request.getTemplateId()));
        } else {
            sendSmsService.sendSms(mobileList, TemplateType.of(request.getTemplateId()), request.getParams().split(CommonConstant.COMMA));
        }
    }

    @Override
    public void signIn(Long memberId) {
        memberScoreService.signIn(memberId);
    }

    @Override
    public SignInVO getSignIn(Long memberId) {
        return memberScoreService.getSignIn(memberId);
    }

    @Override
    public void updateScore(Long memberId, ScoreType scoreType, Integer score, String remark) {
        memberScoreService.updateScore(memberId, scoreType, score, remark);
    }
}
