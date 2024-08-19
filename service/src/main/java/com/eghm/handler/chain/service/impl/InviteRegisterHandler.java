package com.eghm.handler.chain.service.impl;

import cn.hutool.core.util.StrUtil;
import com.eghm.dto.ext.MemberRegister;
import com.eghm.handler.chain.Handler;
import com.eghm.handler.chain.HandlerInvoker;
import com.eghm.handler.chain.MessageData;
import com.eghm.handler.chain.annotation.HandlerEnum;
import com.eghm.handler.chain.annotation.HandlerMark;
import com.eghm.model.Member;
import com.eghm.model.MemberInviteLog;
import com.eghm.service.member.MemberInviteLogService;
import com.eghm.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * 邀请记录
 *
 * @author 殿小二
 * @since 2020/9/14
 */
@Service("inviteRegisterHandler")
@Order(30)
@Slf4j
@HandlerMark(HandlerEnum.REGISTER)
@RequiredArgsConstructor
public class InviteRegisterHandler implements Handler {

    private final MemberService memberService;

    private final MemberInviteLogService memberInviteLogService;

    @Override
    public void doHandler(Object messageData, HandlerInvoker invoker) {
        MessageData data = (MessageData) messageData;
        log.info("注册添加邀请记录");
        MemberRegister register = data.getMemberRegister();
        Member dataMember = data.getMember();
        if (StrUtil.isNotBlank(register.getInviteCode())) {
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
