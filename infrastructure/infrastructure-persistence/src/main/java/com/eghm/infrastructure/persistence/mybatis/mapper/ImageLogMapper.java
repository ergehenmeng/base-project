package com.eghm.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.application.shared.dto.operate.image.ImageQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.po.ImageLogPO;
import com.eghm.application.shared.vo.operate.log.ImageLogResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface ImageLogMapper extends BaseMapper<ImageLogPO> {

    /**
     * 分页查询
     *
     * @param page    分页对象
     * @param request 查询参数
     * @return 列表
     */
    Page<ImageLogResponse> getByPage(Page<ImageLogResponse> page, @Param("param") ImageQueryRequest request);
}
