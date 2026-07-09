package com.eghm.infrastructure.persistence.mybatis.operate.query;

import com.eghm.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.dto.operate.version.VersionQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.AppVersionMapper;
import com.eghm.application.operate.service.AppVersionQueryGateway;
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





