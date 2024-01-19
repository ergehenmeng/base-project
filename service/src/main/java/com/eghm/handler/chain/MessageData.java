package com.eghm.handler.chain;

import com.eghm.dto.ext.MemberRegister;
import com.eghm.model.Member;
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
