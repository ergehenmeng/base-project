package com.eghm.infrastructure.persistence.mybatis.operate.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.operate.image.ImageQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.ImageLogMapper;
import com.eghm.application.operate.query.ImageLogQueryService;
import com.eghm.application.shared.vo.operate.log.ImageLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 图片上传记录 MyBatis 查询适配器
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisImageLogQueryService implements ImageLogQueryService {

    private final ImageLogMapper imageLogMapper;

    @Override
    public Page<ImageLogResponse> getByPage(Page<ImageLogResponse> page, ImageQueryRequest request) {
        return MybatisPageUtil.fromMybatis(imageLogMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }
}





