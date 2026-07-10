package com.eghm.application.operate.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.operate.version.VersionQueryRequest;
import com.eghm.application.shared.vo.operate.version.AppVersionResponse;
import com.eghm.application.shared.vo.operate.version.AppVersionVO;

/**
 * 手机版本查询服务
 *
 * @author 二哥很猛
 */
public interface AppVersionQueryService {

    /**
     * 分页查询
     *
     * @param page    分页
     * @param request 查询条件
     * @return 分页结果
     */
    Page<AppVersionResponse> getByPage(Page<AppVersionResponse> page, VersionQueryRequest request);

    /**
     * 查询客户端当前渠道的最新版本读模型.
     *
     * @return 版本信息
     */
    AppVersionVO getLatestVersion();
}
