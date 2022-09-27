package com.eghm.mapper;

import com.eghm.model.LineDayConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 线路日配置信息 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-26
 */
public interface LineDayConfigMapper extends BaseMapper<LineDayConfig> {

    /**
     * 新增或编辑线路日配置信息
     * @param config 配置信息
     */
    int insertOrUpdate(LineDayConfig config);
}
