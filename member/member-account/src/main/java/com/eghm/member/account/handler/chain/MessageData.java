package com.eghm.member.account.handler.chain;

import com.eghm.member.account.dto.MemberRegister;
import com.eghm.member.account.entity.Member;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2018/12/19 17:55
 */
@Data
public class MessageData {

    private Member member;

    private MemberRegister memberRegister;

}
