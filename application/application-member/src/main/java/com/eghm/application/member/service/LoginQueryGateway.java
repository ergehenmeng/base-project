package com.eghm.application.member.service;

import com.eghm.dto.ext.Page;
import com.eghm.domain.member.model.LoginDevice;
import com.eghm.dto.business.member.LoginLogQueryRequest;
import com.eghm.vo.business.member.LoginLogResponse;

import java.util.List;

/**
 * Query port for login logs and devices.
 */
public interface LoginQueryGateway {

    Page<LoginLogResponse> listLoginLog(LoginLogQueryRequest request);

    List<LoginDevice> listDeviceByMemberId(Long memberId);
}
