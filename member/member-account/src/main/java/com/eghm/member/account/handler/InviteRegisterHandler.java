package com.eghm.member.account.handler;

import com.eghm.member.account.dto.MemberRegister;
import com.eghm.member.account.handler.chain.Handler;
import com.eghm.member.account.handler.chain.HandlerInvoker;
import com.eghm.member.account.handler.chain.MessageData;
import com.eghm.member.account.handler.chain.enums.HandlerEnum;
import com.eghm.member.account.handler.chain.annotation.HandlerMark;
import com.eghm.member.account.entity.Member;
import com.eghm.member.engagement.entity.MemberInviteLog;
import com.eghm.member.engagement.service.MemberInviteLogService;
import com.eghm.member.account.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import static com.eghm.foundation.core.utils.StringUtil.isNotBlank;

/**
 * 邀请记录
 *
 * @author 殿小二
 * @since 2020/9/14
 */
@Slf4j
@Service
@Order(30)
@RequiredArgsConstructor
@HandlerMark(HandlerEnum.REGISTER)
public class InviteRegisterHandler implements Handler {

    private final MemberService memberService;

    private final MemberInviteLogService memberInviteLogService;

    @Override
    public void doHandler(Object messageData, HandlerInvoker invoker) {
        MessageData data = (MessageData) messageData;
        MemberRegister register = data.getMemberRegister();
        Member dataMember = data.getMember();
        if (isNotBlank(register.getInviteCode())) {
            log.info("会员注册新增邀请记录");
            Member member = memberService.getByInviteCode(register.getInviteCode());
            if (member != null) {
                MemberInviteLog inviteLog = new MemberInviteLog();
                inviteLog.setMemberId(member.getId());
                inviteLog.setInviteMemberId(dataMember.getId());
                memberInviteLogService.insert(inviteLog);
            } else {
                log.warn("用户输入的邀请码无效 memberId:[{}] ,inviteCode:[{}]", dataMember.getId(), register.getInviteCode());
            }
        }
        invoker.doHandler(messageData);
    }

}
