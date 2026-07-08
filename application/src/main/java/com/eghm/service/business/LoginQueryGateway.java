package com.eghm.service.business;

import com.eghm.dto.ext.Page;
import com.eghm.business.model.LoginDevice;
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
