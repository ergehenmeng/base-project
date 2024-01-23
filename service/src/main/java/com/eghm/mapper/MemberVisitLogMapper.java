package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dto.statistics.VisitRequest;
import com.eghm.model.MemberVisitLog;
import com.eghm.vo.business.statistics.MemberVisitVO;

import java.util.List;

/**
 * <p>
 * 用户浏览记录表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-23
 */
public interface MemberVisitLogMapper extends BaseMapper<MemberVisitLog> {

    /**
     * 按天统计访问量
     *
     * @param request 查询条件
     * @return 访问量
     */
    List<MemberVisitVO> dayVisit(VisitRequest request);
}
