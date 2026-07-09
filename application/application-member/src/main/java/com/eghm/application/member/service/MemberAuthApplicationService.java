package com.eghm.application.member.service;

import com.eghm.application.shared.dto.sys.login.AccountLoginDTO;
import com.eghm.application.shared.dto.sys.login.DoubleCheckDTO;
import com.eghm.application.shared.dto.sys.login.SmsLoginDTO;
import com.eghm.domain.member.model.Member;
import com.eghm.application.shared.vo.login.LoginTokenVO;

/**
 * 会员认证服务 - 负责所有登录相关操作
 *
 * @author 二哥很猛
 * @since 2019/8/19 15:50
 */
public interface MemberAuthApplicationService {

    LoginTokenVO accountLogin(AccountLoginDTO login);

    LoginTokenVO doubleCheck(DoubleCheckDTO dto);

    LoginTokenVO smsLogin(SmsLoginDTO login);

    void sendLoginSms(String mobile, String ip);

    void offline(Long memberId);

    LoginTokenVO doLogin(Member member, String ip);
}
