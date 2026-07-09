package com.eghm.domain.member.event;

import com.eghm.domain.member.model.Member;
import com.eghm.domain.member.valueobject.MemberRegistrationInfo;
import com.eghm.domain.shared.event.DomainEvent;
import lombok.Getter;

/**
 * 成员注册事件
 *
 * @author 二哥很猛
 * @since 2024/01/01
 */
@Getter
public class MemberRegisteredEvent extends DomainEvent {

    /**
     * 新注册的成员
     */
    private final Member member;

    /**
     * 注册信息
     */
    private final MemberRegistrationInfo registrationInfo;

    public MemberRegisteredEvent(Member member, MemberRegistrationInfo registrationInfo) {
        super();
        this.member = member;
        this.registrationInfo = registrationInfo;
    }
}
