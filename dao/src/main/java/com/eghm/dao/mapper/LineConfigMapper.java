package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.LineConfig;

/**
 * <p>
 * 线路商品配置表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-26
 */
public interface LineConfigMapper extends BaseMapper<LineConfig> {

    /**
     * 新增或更新线路日态信息
     * @param config 线路日态
     * @return 条数
     */
    int insertOrUpdate(LineConfig config);
    
    /**
     * 更新库存信息
     * @param id id
     * @param num 正数+库存 负数-库存
     * @return 1
     */
    int updateStock(Long id, Integer num);
}
