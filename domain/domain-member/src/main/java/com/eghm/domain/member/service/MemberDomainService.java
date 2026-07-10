package com.eghm.domain.member.service;

import com.eghm.domain.member.repository.MemberRepository;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;

/**
 * 会员领域服务.
 *
 * @author 二哥很猛
 */
public class MemberDomainService {

    public void assertMobileAvailable(MemberRepository repository, String mobile) {
        if (repository.existsByMobile(mobile)) {
            throw new BusinessException(ErrorCode.MOBILE_REGISTER_REDO);
        }
    }

    public void assertAccountAvailable(MemberRepository repository, String account) {
        if (repository.existsByAccount(account)) {
            throw new BusinessException(ErrorCode.ACCOUNT_REGISTER_REDO);
        }
    }

    public void assertEmailAvailable(MemberRepository repository, String email) {
        if (repository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.EMAIL_REDO_BIND);
        }
    }
}
