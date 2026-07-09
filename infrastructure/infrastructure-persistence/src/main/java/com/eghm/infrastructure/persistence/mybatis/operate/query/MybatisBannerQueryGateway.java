package com.eghm.infrastructure.persistence.mybatis.operate.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.operate.banner.BannerQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.BannerMapper;
import com.eghm.application.operate.port.out.BannerQueryGateway;
import com.eghm.application.shared.vo.operate.banner.BannerResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 轮播图 MyBatis 查询适配器
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisBannerQueryGateway implements BannerQueryGateway {

    private final BannerMapper bannerMapper;

    @Override
    public Page<BannerResponse> getByPage(Page<BannerResponse> page, BannerQueryRequest request) {
        return MybatisPageUtil.fromMybatis(bannerMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }
}





