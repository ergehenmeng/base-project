package com.eghm.infrastructure.persistence.mybatis.operate.repository;

import com.eghm.infrastructure.persistence.mybatis.mapper.ImageLogMapper;
import com.eghm.domain.operate.model.ImageLog;
import com.eghm.domain.operate.repository.ImageLogRepository;
import com.eghm.infrastructure.persistence.mybatis.po.ImageLogPO;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 图片上传记录 MyBatis 仓储适配器
 *
 * @author 二哥很猛
 */
@Repository
@AllArgsConstructor
public class MybatisImageLogRepository implements ImageLogRepository {

    private final ImageLogMapper imageLogMapper;

    @Override
    public void save(ImageLog imageLog) {
        imageLogMapper.insert(toPo(imageLog));
    }

    @Override
    public void update(ImageLog imageLog) {
        imageLogMapper.updateById(toPo(imageLog));
    }

    @Override
    public void deleteById(Long id) {
        imageLogMapper.deleteById(id);
    }

    private ImageLogPO toPo(ImageLog imageLog) {
        return DataUtil.copy(imageLog, ImageLogPO.class);
    }
}
