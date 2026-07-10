package com.eghm.application.member.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.domain.member.model.LoginDevice;
import com.eghm.application.shared.dto.business.member.LoginLogQueryRequest;
import com.eghm.application.shared.vo.business.member.LoginLogResponse;

import java.util.List;

/**
 * Login log and device query service.
 */
public interface LoginQueryService {

    Page<LoginLogResponse> listLoginLog(LoginLogQueryRequest request);

    List<LoginDevice> listDeviceByMemberId(Long memberId);
}
