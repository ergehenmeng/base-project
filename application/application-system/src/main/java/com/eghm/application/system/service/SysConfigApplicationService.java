package com.eghm.application.system.service;

import com.eghm.application.shared.dto.sys.config.ConfigEditRequest;


/**
 * @author 二哥很猛
 * @since 2018/1/12 09:45
 */
public interface SysConfigApplicationService {

    /**
     * 更新系统参数
     *
     * @param request 待更新的参数对象
     */
    void update(ConfigEditRequest request);

}
