package com.eghm.query.operate;

import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.operate.version.VersionQueryRequest;
import com.eghm.mapper.AppVersionMapper;
import com.eghm.service.operate.AppVersionQueryGateway;
import com.eghm.vo.operate.version.AppVersionResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 手机版本 MyBatis 查询适配器
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisAppVersionQueryGateway implements AppVersionQueryGateway {

    private final AppVersionMapper appVersionMapper;

    @Override
    public Page<AppVersionResponse> getByPage(Page<AppVersionResponse> page, VersionQueryRequest request) {
        return MybatisPageUtil.fromMybatis(appVersionMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }
}





