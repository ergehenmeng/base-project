package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.LineOrderSnapshot;
import com.eghm.vo.business.order.line.LineOrderSnapshotVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 线路订单每日行程配置快照表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-09-01
 */
public interface LineOrderSnapshotMapper extends BaseMapper<LineOrderSnapshot> {

    /**
     * 获取线路订单每日行程配置快照列表
     *
     * @param orderNo 订单编号
     * @return 线路订单每日行程配置快照列表
     */
    List<LineOrderSnapshotVO> getList(@Param("orderNo") String orderNo);

}
