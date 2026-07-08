package com.eghm.query.operate;

import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.operate.image.ImageQueryRequest;
import com.eghm.mapper.ImageLogMapper;
import com.eghm.service.operate.ImageLogQueryGateway;
import com.eghm.vo.operate.log.ImageLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 图片上传记录 MyBatis 查询适配器
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisImageLogQueryGateway implements ImageLogQueryGateway {

    private final ImageLogMapper imageLogMapper;

    @Override
    public Page<ImageLogResponse> getByPage(Page<ImageLogResponse> page, ImageQueryRequest request) {
        return MybatisPageUtil.fromMybatis(imageLogMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }
}





