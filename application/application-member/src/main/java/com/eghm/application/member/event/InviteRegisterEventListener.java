package com.eghm.application.member.event;

import com.eghm.domain.member.model.Member;
import com.eghm.domain.member.model.MemberInviteLog;
import com.eghm.domain.member.event.MemberRegisteredEvent;
import com.eghm.application.member.port.in.MemberInviteLogService;
import com.eghm.application.member.port.in.MemberService;
import com.eghm.application.shared.dto.ext.MemberRegister;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.eghm.application.shared.utils.StringUtil.isNotBlank;

/**
 * 邀请记录事件监听器
 *
 * @author 殿小二
 * @since 2020/9/14
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InviteRegisterEventListener {

    private final MemberService memberService;

    private final MemberInviteLogService memberInviteLogService;

    @EventListener
    public void onMemberRegistered(MemberRegisteredEvent event) {
        Member member = event.getMember();
        MemberRegister register = event.getMemberRegister();

        if (isNotBlank(register.getInviteCode())) {
            log.info("会员注册新增邀请记录");
            Member inviter = memberService.getByInviteCode(register.getInviteCode());
            if (inviter != null) {
                MemberInviteLog inviteLog = new MemberInviteLog();
                inviteLog.setMemberId(inviter.getId());
                inviteLog.setInviteMemberId(member.getId());
                memberInviteLogService.insert(inviteLog);
            } else {
                log.warn("用户输入的邀请码无效 memberId:[{}] ,inviteCode:[{}]", member.getId(), register.getInviteCode());
            }
        }
    }
}
