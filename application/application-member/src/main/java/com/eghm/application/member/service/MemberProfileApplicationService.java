package com.eghm.application.member.service;

import com.eghm.application.shared.dto.business.member.BindEmailDTO;
import com.eghm.application.shared.dto.business.member.ChangeEmailDTO;
import com.eghm.application.shared.dto.business.member.MemberDTO;
import com.eghm.application.shared.dto.business.member.SendEmailAuthCodeDTO;
import com.eghm.domain.member.model.Member;
import com.eghm.application.shared.vo.business.member.MemberVO;

/**
 * 会员资料服务 - 负责用户资料、邮箱、密码相关操作
 *
 * @author 二哥很猛
 * @since 2019/8/19 15:50
 */
public interface MemberProfileApplicationService {

    void sendForgetSms(String mobile, String ip);

    void sendBindEmail(String email, Long memberId);

    void bindEmail(BindEmailDTO request);

    void sendChangeEmailSms(Long memberId, String ip);

    void sendChangeEmailCode(SendEmailAuthCodeDTO request);

    void changeEmail(ChangeEmailDTO request);

    void setPassword(String requestId, String password);

    MemberVO memberHome(Long memberId);

    void edit(Long memberId, MemberDTO dto);

    Member getByInviteCode(String inviteCode);
}
