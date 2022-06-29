package com.eghm.dao.mapper;

import com.eghm.dao.model.HomestayRoomConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 房间价格配置表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-06-25
 */
public interface HomestayRoomConfigMapper extends BaseMapper<HomestayRoomConfig> {

    /**
     * 新增或更新房态信息
     * @param config 房态信息
     * @return 条数
     */
    int insertOrUpdate(HomestayRoomConfig config);
}
