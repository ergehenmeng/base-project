package com.eghm.application.member.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.business.member.LoginLogQueryRequest;
import com.eghm.application.shared.vo.business.member.LoginDeviceVO;
import com.eghm.application.shared.vo.business.member.LoginLogResponse;

import java.util.List;

/**
 * Login log and device query service.
 */
public interface LoginQueryService {

    Page<LoginLogResponse> listLoginLog(LoginLogQueryRequest request);

    List<LoginDeviceVO> listDeviceByMemberId(Long memberId);
}
