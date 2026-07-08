package com.eghm.query.operate;

import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.operate.banner.BannerQueryRequest;
import com.eghm.mapper.BannerMapper;
import com.eghm.service.operate.BannerQueryGateway;
import com.eghm.vo.operate.banner.BannerResponse;
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





